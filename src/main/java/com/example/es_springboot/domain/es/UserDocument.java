package com.example.es_springboot.domain.es;

import com.example.es_springboot.domain.BasicProfile;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;


@Document(indexName = "user_index")
@Entity
public class UserDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BasicProfile basicProfile;

    protected UserDocument() {
    }

    public UserDocument(String name) {
        this(name, null);
    }

    public UserDocument(String name, String description) {
        this(null, new BasicProfile(name, description));
    }

    @PersistenceConstructor
    public UserDocument(Long id, BasicProfile basicProfile) {
        this.id = id;
        this.basicProfile = basicProfile;
    }

    public Long getId() {
        return id;
    }

    public BasicProfile getBasicProfile() {
        return basicProfile;
    }
}
