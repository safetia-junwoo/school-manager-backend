package com.base.boilerplate.util;

import org.springframework.stereotype.Repository;

@Repository
public interface OwnerIdentifiable {
    Integer getOwnerId();
    String getFailCode();
}
