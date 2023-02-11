package com.pro2111.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.pro2111.service.CachingService;

@Service
public class CachingServiceImpl implements CachingService {
	@Autowired
	CacheManager cacheManager;

	/**
	 * Xóa bộ nhớ catch
	 */
	@Override
	public void evictAllCaches() {
		cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}

}
