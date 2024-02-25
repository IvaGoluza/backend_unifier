package hr.fer.unifier.backend.service.impl;


import hr.fer.unifier.backend.api.user.AllUsersDTO;
import hr.fer.unifier.backend.api.user.profile.OrganizationProfileDTO;
import hr.fer.unifier.backend.api.user.profile.PersonProfileDTO;
import hr.fer.unifier.backend.db.user.OrganizationDao;
import hr.fer.unifier.backend.db.user.PersonDao;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  private final PersonDao personDao;

  private final OrganizationDao organizationDao;

  private final ModelMapper modelMapper;

  @Transactional(readOnly = true)
  @Override
  public AllUsersDTO getAllUsers() {
    final List<PersonProfileDTO> volunteers = personDao.findAll()
            .stream()
            .sorted(Comparator.comparing(User::getId))
            .map(volunteer -> modelMapper.map(volunteer, PersonProfileDTO.class))
            .toList();

    final List<OrganizationProfileDTO> organizations = organizationDao.findAll()
            .stream()
            .sorted(Comparator.comparing(User::getId))
            .map(organization -> modelMapper.map(organization, OrganizationProfileDTO.class))
            .toList();

    return new AllUsersDTO(volunteers, organizations);
  }

  @Transactional
  @Override
  public void changeBlockStatus(Long userId) {
    final User user = userDao.findById(userId).orElseThrow(
        () -> new EntityNotFoundException("User with id " + userId + " does not exist.")
    );

    user.setBlocked(!user.isBlocked());
  }

  @Transactional
  @Override
  public void approveUser(Long userId) {
    final User user = userDao.findById(userId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't find user with id %d", userId))
    );

    user.setApproved(true);
  }
}
