package com.base.boilerplate.util;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CacheUtils {
    private final CacheManager cacheManager;

    // 저장되어 있는 캐시 현황
    @PostMapping("/public/cache")
    public ResponseEntity<Map<String, ConcurrentMap<Object, Object>>> localCacheValueInfo() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        Map<String, ConcurrentMap<Object, Object>> response = new HashMap<>(cacheNames.size());
        for (String cacheName : cacheNames) {
            CaffeineCache cache = (CaffeineCache) cacheManager.getCache(cacheName);
            if (cache != null) {
                Cache<Object, Object> nativeCache = cache.getNativeCache();
                response.put(cacheName, nativeCache.asMap());
            }
        }
        return ResponseEntity.ok(response);
    }

    // 저장 되어있는 캐시의 hit, miss 현황
    // 자바 1.8 에서는 지원 되지 않는 메소드
//    @PostMapping("/public/cache/key")
//    public ResponseEntity<List<Map<String, String>>> localCacheStatInfo() {
//
//        Collection<String> cacheNames = cacheManager.getCacheNames();
//        List<Map<String, String>> response = new ArrayList<>(cacheNames.size());
//        for (String cacheName : cacheNames) {
//            CaffeineCache cache = (CaffeineCache) cacheManager.getCache(cacheName);
//            if (cache != null) {
//                Cache<Object, Object> nativeCache = cache.getNativeCache();
//                response.add(Map.of(cacheName, nativeCache.stats().toString()));
//            }
//        }
//        return ResponseEntity.ok(response);
//    }

    // 저장되어 있는 캐시 전부 삭제
    @ResponseBody
    @PostMapping("/public/clearAll")
    public ResponseEntity<HashMap> clearAllCache() {

        List<String> cacheNameList = new LinkedList<>();

        for (String cacheName : cacheManager.getCacheNames()) {
            cacheNameList.add(cacheName);
            Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
        }

        HashMap<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("msg", "success remove all cache");
        rtnMap.put("removedCacheNameList", cacheNameList);
        return ResponseEntity.ok(rtnMap);
    }
}
