package com.szjz.seller.repository;

import com.szjz.model.base.BaseRepository;
import com.szjz.model.Sign;

/**
 * author:szjz
 * date:2019/6/23
 */
public interface SignRepository extends BaseRepository<Sign> {
    Sign findByAuthId(String authId);
}
