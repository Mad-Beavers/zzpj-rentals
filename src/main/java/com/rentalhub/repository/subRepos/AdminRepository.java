package com.rentalhub.repository.subRepos;

import com.rentalhub.model.Admin;
import com.rentalhub.repository.UserRepositoryBase;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminRepository extends UserRepositoryBase<Admin> {
}
