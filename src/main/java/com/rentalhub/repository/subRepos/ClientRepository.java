package com.rentalhub.repository.subRepos;

import com.rentalhub.model.Client;
import com.rentalhub.repository.UserRepositoryBase;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ClientRepository extends UserRepositoryBase<Client> {
}
