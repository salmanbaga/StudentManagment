package com.Project.Student.Repo;

import com.Project.Student.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users,Integer> {

    Optional<Users> findByuserName(String userName);

}
