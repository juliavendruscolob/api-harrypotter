package springbootapi.springbootapiharrypotter.repository;

import springbootapi.springbootapiharrypotter.entity.Character;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {
}
