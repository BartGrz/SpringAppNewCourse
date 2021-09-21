package com.udemyspringappdemo.demo.model;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
 class Audit {

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    private void prePersist(){
        createdOn = LocalDateTime.now();
    }
    @PreUpdate
    private void preMerge () {
        updatedOn = LocalDateTime.now();
    }
}
