package com.example.shop.service;


import com.example.shop.configuration.RedissonConfiguration;
import com.example.shop.exception.ProductSoldOutException;
import com.example.shop.exception.ProductTryLockFail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final RedissonClient redissonClient;
    private final RedissonConfiguration redissonConfiguration;
    private static final int MAX_SELLABLE_COUNT = 3;


    public String getLockName(int productId){
        final String prefix = "%s-lock";
        return String.format(prefix, productId);
    }

    public String getKey(int productId) {
        return String.valueOf(productId);
    }

    public void increaseSellCount(final int productId, final int count){
        final String key = getKey(productId);
        final String lockName = getLockName(productId);
        final RLock lock = redissonClient.getLock(lockName);
        final String worker = Thread.currentThread().getName();

        int currentSellCount;
        try {
            if(!lock.tryLock(1, 3, TimeUnit.SECONDS))
                throw new ProductTryLockFail();
                //return;

            currentSellCount = getCurrentSellCount(key);
            if(currentSellCount + count > MAX_SELLABLE_COUNT) {
                log.info("[{}] 모두 팔렸음!!! ({}개)", worker, currentSellCount + count);
                throw new ProductSoldOutException();
                //return;
            }

            setSellCount(key, currentSellCount + count);
            log.info("현재 진행중인 사람 : {} & 현재 팔린 갯수 : {}개", worker, currentSellCount + count);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public void setSellCount(String key, int amount){

        redissonClient.getBucket(key).set(amount);
    }

    public int getCurrentSellCount(String key) {
        if(redissonClient.getBucket(key).get() == null)
            return 0;

        return (int) redissonClient.getBucket(key).get();
    }

    public int getProductSellCount(int productId) {
        return getCurrentSellCount(getKey(productId));
    }

}
