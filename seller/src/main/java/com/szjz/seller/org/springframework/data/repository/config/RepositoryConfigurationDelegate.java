/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.repository.config; //虽然报错 但是不要改动，这样才会替代源码对应的类被当做源码运行

import java.util.ArrayList;
import java.util.List;

//import javax.annotation.Nullable;

import com.szjz.seller.org.springframework.data.repository.config.RepositoryBeanNamePrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.*;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
//import org.springframework.lang.Nullable;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

/**
 * Delegate for configuration integration to reuse the general way of detecting repositories. Customization is done by
 * providing a configuration format specific {@link RepositoryConfigurationSource} (currently either XML or annotations
 * are supported). The actual registration can then be triggered for different {@link RepositoryConfigurationExtension}
 * s.
 *
 * @author Oliver Gierke
 * @author Jens Schauder
 */
public class RepositoryConfigurationDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryConfigurationDelegate.class);

    private static final String REPOSITORY_REGISTRATION = "Spring Data {} - Registering repository: {} - Interface: {} - Factory: {}";
    private static final String MULTIPLE_MODULES = "Multiple Spring Data modules found, entering strict repository configuration mode!";

    static final String FACTORY_BEAN_OBJECT_TYPE = "factoryBeanObjectType";

    private final RepositoryConfigurationSource configurationSource;
    private final ResourceLoader resourceLoader;
    private final Environment environment;
    private final boolean isXml;
    private final boolean inMultiStoreMode;

    /**
     * Creates a new {@link RepositoryConfigurationDelegate} for the given {@link RepositoryConfigurationSource} and
     * {@link ResourceLoader} and {@link Environment}.
     *
     * @param configurationSource must not be {@literal null}.
     * @param resourceLoader      must not be {@literal null}.
     * @param environment         must not be {@literal null}.
     */
    public RepositoryConfigurationDelegate(RepositoryConfigurationSource configurationSource,
                                           ResourceLoader resourceLoader, Environment environment) {

        this.isXml = configurationSource instanceof XmlRepositoryConfigurationSource;
        boolean isAnnotation = configurationSource instanceof AnnotationRepositoryConfigurationSource;

        Assert.isTrue(isXml || isAnnotation,
                "Configuration source must either be an Xml- or an AnnotationBasedConfigurationSource!");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null!");

        this.configurationSource = configurationSource;
        this.resourceLoader = resourceLoader;
        this.environment = defaultEnvironment(environment, resourceLoader);
        this.inMultiStoreMode = multipleStoresDetected();
    }

    /**
     * Defaults the environment in case the given one is null. Used as fallback, in case the legacy constructor was
     * invoked.
     *
     * @param environment    can be {@literal null}.
     * @param resourceLoader can be {@literal null}.
     * @return
     */
    private static Environment defaultEnvironment(@Nullable Environment environment,
                                                  @Nullable ResourceLoader resourceLoader) {

        if (environment != null) {
            return environment;
        }

        return resourceLoader instanceof EnvironmentCapable ? ((EnvironmentCapable) resourceLoader).getEnvironment()
                : new StandardEnvironment();
    }

    /**
     * Registers the found repositories in the given {@link BeanDefinitionRegistry}.
     *
     * @param registry
     * @param extension
     * @return {@link BeanComponentDefinition}s for all repository bean definitions found.
     */
    public List<BeanComponentDefinition> registerRepositoriesIn(BeanDefinitionRegistry registry,
                                                                RepositoryConfigurationExtension extension) {

        extension.registerBeansForRoot(registry, configurationSource);

        RepositoryBeanDefinitionBuilder builder = new RepositoryBeanDefinitionBuilder(registry, extension, resourceLoader,
                environment);
        List<BeanComponentDefinition> definitions = new ArrayList<>();

        for (RepositoryConfiguration<? extends RepositoryConfigurationSource> configuration : extension
                .getRepositoryConfigurations(configurationSource, resourceLoader, inMultiStoreMode)) {

            BeanDefinitionBuilder definitionBuilder = builder.build(configuration);

            extension.postProcess(definitionBuilder, configurationSource);

            if (isXml) {
                extension.postProcess(definitionBuilder, (XmlRepositoryConfigurationSource) configurationSource);
            } else {
                extension.postProcess(definitionBuilder, (AnnotationRepositoryConfigurationSource) configurationSource);
            }

            AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();
            String beanName = configurationSource.generateBeanName(beanDefinition);

            AnnotationMetadata metadata = (AnnotationMetadata) configurationSource.getSource();

            //self------------
            //判断配置类是否使用了primary进行了标注，如果有就设置为primary
            if (metadata.hasAnnotation(Primary.class.getName())) {
                beanDefinition.setPrimary(true);
            }
            //在判断是否使用了RepositoryBeanNamePrefix进行了标注，如果有添加名称前缀
            else if (metadata.hasAnnotation(RepositoryBeanNamePrefix.class.getName())) {
                MultiValueMap<String, Object> map = metadata.getAllAnnotationAttributes(RepositoryBeanNamePrefix.class.getName());
                List<Object> value = map.get("value");
                String prefix = (String) value.get(0);
                //将第一个字母改为大写 符合驼峰命名法
                StringBuffer beanNameBuf = new StringBuffer(beanName);
                String upperCase = beanName.substring(0, 1).toUpperCase();
                beanNameBuf = beanNameBuf.replace(0,1,upperCase);
                beanName = prefix + new String(beanNameBuf);
                System.err.println(beanName);
            }


            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(REPOSITORY_REGISTRATION, extension.getModuleName(), beanName,
                        configuration.getRepositoryInterface(), configuration.getRepositoryFactoryBeanClassName());
            }

            beanDefinition.setAttribute(FACTORY_BEAN_OBJECT_TYPE, configuration.getRepositoryInterface());

            registry.registerBeanDefinition(beanName, beanDefinition);
            definitions.add(new BeanComponentDefinition(beanDefinition, beanName));
        }

        return definitions;
    }

    /**
     * Scans {@code repository.support} packages for implementations of {@link RepositoryFactorySupport}. Finding more
     * than a single type is considered a multi-store configuration scenario which will trigger stricter repository
     * scanning.
     *
     * @return
     */
    private boolean multipleStoresDetected() {

        boolean multipleModulesFound = SpringFactoriesLoader
                .loadFactoryNames(RepositoryFactorySupport.class, resourceLoader.getClassLoader()).size() > 1;

        if (multipleModulesFound) {
            LOGGER.info(MULTIPLE_MODULES);
        }

        return multipleModulesFound;
    }
}
