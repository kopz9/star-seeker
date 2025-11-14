package me.kopz.starseeker.repository;

import me.kopz.starseeker.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
