package hr.fer.unifier.backend.service;


import hr.fer.unifier.backend.api.user.AllUsersDTO;

public interface UserService {
  AllUsersDTO getAllUsers();

  void changeBlockStatus(Long userId);

  void approveUser(Long userId);
}
