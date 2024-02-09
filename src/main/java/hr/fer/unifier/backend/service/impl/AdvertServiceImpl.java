package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.api.advert.AdvertDTO;
import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.db.entity.User;
import hr.fer.unifier.backend.db.AdvertDao;
import hr.fer.unifier.backend.db.UserDao;
import hr.fer.unifier.backend.service.AdvertService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

  private final AdvertDao advertDao;

  private final UserDao userDao;

  private final ModelMapper modelMapper;

  @Transactional
  @Override
  public AdvertResponseDTO saveAdvert(AdvertDTO advertDTO) {
    Advert advert = modelMapper.map(advertDTO, Advert.class);
    User advertUser = userDao.findById(advertDTO.getUserId()).orElseThrow(() ->
            new EntityNotFoundException("User with id " + advertDTO.getUserId() + " does not exist.")
    );
    advert.setUser(advertUser);
    advert.setDeleted(false);
    advert = advertDao.save(advert);
    return modelMapper.map(advert, AdvertResponseDTO.class);
  }

  @Transactional
  @Override
  public void deleteAdvert(Long id) {
    Advert advert = advertDao.findById(id).orElseThrow(() ->  new EntityNotFoundException("Advert with id:" + id + " does not exist."));
    advert.setDeleted(true);
    advertDao.save(advert);
  }

  @Transactional(readOnly = true)
  @Override
  public List<AdvertResponseDTO> getAdverts(Long userId) {
    final User user = userDao.findById(userId).orElseThrow(() ->
      new EntityNotFoundException("User with id " + userId + " does not exist.")
    );

    List<Advert> adverts = advertDao.findByUserAndDeletedFalse(user).orElse(Collections.emptyList());

    return adverts.stream().map(advert -> modelMapper.map(advert, AdvertResponseDTO.class)).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  @Override
  public List<AdvertResponseDTO> getAllAdverts() {
    return advertDao.findAdvertsByDeletedFalse()
            .orElse(Collections.emptyList())
            .stream()
            .map(advert -> modelMapper.map(advert, AdvertResponseDTO.class))
            .toList();
  }

}
