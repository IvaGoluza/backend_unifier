package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.api.advert.AdvertDTO;
import hr.fer.unifier.backend.api.advert.AdvertResponseDTO;
import hr.fer.unifier.backend.db.entity.User;
import hr.fer.unifier.backend.db.AdvertRepository;
import hr.fer.unifier.backend.db.UserRepository;
import hr.fer.unifier.backend.service.AdvertService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertServiceImpl implements AdvertService {

  private final AdvertRepository advertRepository;

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;


  @Autowired
  public AdvertServiceImpl(AdvertRepository advertRepository, UserRepository userRepository, ModelMapper modelMapper) {
    this.advertRepository = advertRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public AdvertResponseDTO saveAdvert(AdvertDTO advertDTO) {
    Advert advert = modelMapper.map(advertDTO, Advert.class);
    User advertUser = userRepository.findById(advertDTO.getUserId()).orElseThrow(() ->
            new EntityNotFoundException("User with id " + advertDTO.getUserId() + " does not exist.")
    );
    advert.setUser(advertUser);
    advert.setDeleted(false);
    advert = advertRepository.save(advert);
    return modelMapper.map(advert, AdvertResponseDTO.class);
  }

  @Override
  public void deleteAdvert(Long id) {
    if (!advertRepository.existsById(id)) {
      throw new EntityNotFoundException("Advert with id:" + id + " does not exist.");
    }
    Advert advert = advertRepository.findById(id).get();
    advert.setDeleted(true);
    advertRepository.save(advert);
  }

  @Override
  public List<AdvertResponseDTO> getAdverts(Long userId) {
    userRepository.findById(userId).orElseThrow(() ->
      new EntityNotFoundException("User with id " + userId + " does not exist.")
    );

    List<Advert> adverts = advertRepository.findAdvertsByUserIdAndDeletedFalse(userId);

    return adverts.stream().map(advert -> modelMapper.map(advert, AdvertResponseDTO.class)).collect(Collectors.toList());
  }

  @Override
  public List<AdvertResponseDTO> getAllAdverts() {
    List<Advert> adverts = advertRepository.findAdvertsByDeletedFalse();
    return adverts.stream().map(advert -> modelMapper.map(advert, AdvertResponseDTO.class)).collect(Collectors.toList());
  }

}
