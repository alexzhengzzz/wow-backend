package com.bo;

import lombok.Data;

@Data
public class SelectListBO<K,V> {
    K key;
    V value;
}
