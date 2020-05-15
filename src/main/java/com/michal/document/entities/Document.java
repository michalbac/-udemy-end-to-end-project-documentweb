package com.michal.document.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Getter
@Setter
@Entity
public class Document {
    @Id
    private long id;
    private String name;
    @Lob
    private byte[] data;
}
