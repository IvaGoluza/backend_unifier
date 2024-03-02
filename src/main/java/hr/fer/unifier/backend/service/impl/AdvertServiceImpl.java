package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.advert.AdvertDTO;
import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.db.AdvertDao;
import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.UserType;
import hr.fer.unifier.backend.mapper.AdvertMapper;
import hr.fer.unifier.backend.service.AdvertService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static hr.fer.unifier.backend.util.FileUtil.validateImage;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

  private final AdvertDao advertDao;

  private final UserDao userDao;

  private final AdvertMapper advertMapper;

  @Transactional
  @Override
  public AdvertResponseDTO saveAdvert(AdvertDTO advertDTO, MultipartFile file) {
    return advertMapper.toAdvertResponseDTO(createAdvert(advertDTO,file));
  }

  @Transactional
  @Override
  public AdvertResponseDTO saveAdvert(AdvertDTO advertDTO) {
    return advertMapper.toAdvertResponseDTO(createAdvert(advertDTO,null));
  }

  @Transactional
  @Override
  public void changeDeleteStatus(Long id) {
    Advert advert = advertDao.findById(id).orElseThrow(() ->  new EntityNotFoundException("Advert with id:" + id + " does not exist."));
    advert.setDeleted(true);
  }

  @Transactional(readOnly = true)
  @Override
  public List<AdvertResponseDTO> getAdverts(Long userId) {
    final User user = userDao.findById(userId).orElseThrow(() ->
      new EntityNotFoundException("User with id " + userId + " does not exist.")
    );

    return advertDao.findByUserAndDeletedFalse(user)
            .orElse(Collections.emptyList())
            .stream()
            .map(advertMapper::toAdvertResponseDTO)
            .toList();
  }

  @Transactional(readOnly = true)
  @Override
  public List<AdvertResponseDTO> getAllAdverts() {
    return advertDao.findAdvertsByDeletedFalse()
            .orElse(Collections.emptyList())
            .stream()
            .map(advertMapper::toAdvertResponseDTO)
            .toList();
  }

  private Advert createAdvert(AdvertDTO advertDTO, MultipartFile file){
    User advertUser = userDao.findById(advertDTO.getUserId()).orElseThrow(() ->
            new EntityNotFoundException("User with id " + advertDTO.getUserId() + " does not exist.")
    );

    if (!advertUser.isApproved()) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Korisnik još nije odobren od strane admina!");
    }

    if (advertUser.getUserType().equals(UserType.PERSON_IN_NEED)){
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Korisnik nema prava za stvaranje volonterskih oglasa!");
    }

    Advert advert = advertDao.save(advertMapper.toAdvert(advertDTO, advertUser));

    if (file != null && !file.isEmpty()){
      try{
        validateImage(file);
        advert.setAdvertImage(file.getBytes());
      }catch (IOException ex){
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't save image.", ex);
      }
    }

    return advert;
  }

}
