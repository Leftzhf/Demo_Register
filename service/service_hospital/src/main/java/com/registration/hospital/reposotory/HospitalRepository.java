package com.registration.hospital.reposotory;

import com.registration.hospital.entity.THospitalSettings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends MongoRepository<THospitalSettings,String> {
}
