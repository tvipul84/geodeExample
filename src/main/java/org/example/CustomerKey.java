package org.example;


import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerKey implements Serializable {
    public int key;

    public CustomerKey(int key) {
        this.key = key;
    }
}
