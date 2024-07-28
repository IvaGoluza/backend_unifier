package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.advert.*;
import hr.fer.unifier.backend.db.AdvertDao;
import hr.fer.unifier.backend.db.DealDao;
import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.entity.Deal;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.OrganizationDao;
import hr.fer.unifier.backend.db.user.PersonDao;
import hr.fer.unifier.backend.db.user.UserDao;
import hr.fer.unifier.backend.db.user.entity.Person;
import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.DealStatusEnum;
import hr.fer.unifier.backend.enums.UserType;
import hr.fer.unifier.backend.mapper.AdvertMapper;
import hr.fer.unifier.backend.service.AdvertService;
import hr.fer.unifier.backend.service.UserService;
import hr.fer.unifier.backend.util.file.FileUtil;
import hr.fer.unifier.backend.util.pagination.PageUtil;
import hr.fer.unifier.backend.util.pagination.UnifierPage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static hr.fer.unifier.backend.util.file.FileUtil.validateImage;
import static hr.fer.unifier.backend.util.specification.AdvertSpecification.*;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

    private final AdvertDao advertDao;

    private final UserDao userDao;

    private final PersonDao personDao;

    private final DealDao dealDao;

    private final OrganizationDao organizationDao;

    private final AdvertMapper advertMapper;

    private final UserService userService;

    @Transactional
    @Override
    public AdvertResponseDTO saveAdvert(AdvertDTO advertDTO, MultipartFile file) {
        return advertMapper.toAdvertResponseDTO(createAdvert(advertDTO, file));
    }

    @Transactional
    @Override
    public AdvertResponseDTO saveAdvert(AdvertDTO advertDTO) {
        return advertMapper.toAdvertResponseDTO(createAdvert(advertDTO, null));
    }

    @Transactional
    @Override
    public void changeDeleteStatus(Long id) {
        Advert advert = advertDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Advert with id:" + id + " does not exist."));
        advert.setDeleted(true);
    }


    @Transactional(readOnly = true)
    @Override
    public UnifierPage<AdvertResponseDTO> getAllAdverts(String city, String category, String helpType, Long userId, Pageable pageable) {
        Specification<Advert> filters = Specification
                .where(StringUtils.isBlank(city) ? null : inCity(city))
                .and(StringUtils.isBlank(category) ? null : hasCategory(category))
                .and(StringUtils.isBlank(helpType) ? null : hasHelpType(helpType));

        return PageUtil.toPage(
                advertDao.findAll(filters)
                        .stream()
                        .filter(advert -> !advert.getDeleted())
                        .map(advertMapper::toAdvertResponseDTO)
                        .peek(this::addUserName)
                        .peek(advertResponseDTO -> addDealStatus(userId, advertResponseDTO))
                        .toList(),
                pageable
        );
    }

    private void addDealStatus(final Long userId, final AdvertResponseDTO advertResponseDTO) {
        final Deal deal = dealDao.findDealByAdvert(
                advertDao.getReferenceById(advertResponseDTO.getAdvertId()),
                userDao.getReferenceById(userId)
        ).orElse(null);

        if(deal != null){
            final String dealStatus = DealStatusEnum.getDealStatus(deal.getAccepted()).name();
            advertResponseDTO.setDealStatus(dealStatus);
        }
    }

    private void addUserName(final AdvertResponseDTO advertResponseDTO) {
        final Long userId = advertResponseDTO.getUser().getId();
        if (personDao.existsById(userId)) {
            final Person person = personDao.findById(userId).orElseThrow();
            final String fullName = String.format("%s %s", person.getFirstName(), person.getLastName());
            advertResponseDTO.getUser().setFullName(fullName);
        } else {
            organizationDao.findById(userId)
                    .ifPresent(organization -> advertResponseDTO.getUser().setFullName(organization.getName()));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public MyAdvertResponse getAdvert(Long userId, Long advertId) {
        userService.getUserById(userId);

        final Advert advert = advertDao.findById(advertId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Advert with id: %d not found.", advertId))
        );

        final MyAdvertResponse myAdvertResponse = advertMapper.toMyAdvertResponse(advert);
        Set<MyAdvertResponse.UserHelperVolunteerDTO> helperVolunteerDTOS = advert.getHelperVolunteers().stream()
                .map(User::getId)
                .map(userService::getUserCardInfo)
                .map(advertMapper::toUserHelperVolunteerDTO)
                .collect(Collectors.toSet());

        myAdvertResponse.setHelperVolunteers(helperVolunteerDTOS);
        return myAdvertResponse;
    }

    @Transactional(readOnly = true)
    @Override
    public UnifierPage<AdvertsInfoDTO> getAdvertsInfo(Long userId, Pageable pageable) {
        final User user = userService.getUserById(userId);
        final Page<Advert> adverts = advertDao.findAllByUserOrderByAdvertIdDesc(user, pageable);
        return PageUtil.map(adverts, advertMapper::toAdvertsInfoDTO);
    }

    @Override
    public AdvertImageDTO getAdvertImageDTO(Long advertId) {
        final Advert advert = advertDao.findById(advertId).orElseThrow(
                () -> new EntityNotFoundException("Ne postoji advert s id " + advertId)
        );

        return new AdvertImageDTO(
                advert.getAdvertImage() == null
                        ? null
                        : FileUtil.convertToBase64(advert.getAdvertImage()
                )
        );
    }

    @Transactional
    @Override
    public void removeHelperVolunteer(Long advertId, Long userId) {
        //TODO: Validation if user is owner of advert
        final Advert advert = advertDao.findById(advertId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Advert with id: %d not found.", advertId))
        );

        final User user = userService.getUserById(userId);

        advert.getHelperVolunteers().remove(user);
    }

    @Transactional
    @Override
    public void addHelperVolunteer(Long advertId, Long userId) {
        //TODO: Validation if user is owner of advert
        final Advert advert = advertDao.findById(advertId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Advert with id: %d not found.", advertId))
        );

        final User user = userService.getUserById(userId);
        if (user.getUserType().equals(UserType.PERSON_IN_NEED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested person is not a volunteer");
        }
        advert.getHelperVolunteers().add(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UnifierPage<AdvertsTitlesDTO> getAdvertTitles(Long userId, Pageable pageable) {
        final User user = userService.getUserById(userId);
        Page<Advert> allRequests = advertDao.findAllByUserOrderByAdvertIdDesc(user, pageable);
        return PageUtil.map(allRequests, advertMapper::toAdvertTitlesDTO);
    }

    private Advert createAdvert(AdvertDTO advertDTO, MultipartFile file) {
        User advertUser = userDao.findById(advertDTO.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("User with id " + advertDTO.getUserId() + " does not exist.")
        );

        if (!advertUser.isApproved()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Niste odobreni od strane admina, ne možete raditi volonterske oglase!");
        }

        if (advertUser.isBlocked()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Trenutno ste blokirani, ne možete raditi volonterske oglase!");
        }

        if (advertUser.getUserType().equals(UserType.PERSON_IN_NEED)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nemate prava za stvaranje volonterskih oglasa!");
        }
        final Set<User> helpers = advertDTO.getHelpersId() == null
                ? Collections.emptySet()
                : advertDTO.getHelpersId().stream()
                .map(userService::getUserById)
                .collect(Collectors.toSet());

        helpers.stream()
                .map(user -> user.getUserType().equals(UserType.PERSON_IN_NEED) ? user : null)
                .filter(Objects::nonNull).findFirst().ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested person is not a volunteer");
                });

        final Advert advert = advertDao.save(advertMapper.toAdvert(advertDTO, advertUser));
        advert.setHelperVolunteers(helpers);
        if (file != null && !file.isEmpty()) {
            try {
                validateImage(file);
                advert.setAdvertImage(file.getBytes());
            } catch (IOException ex) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't save image.", ex);
            }
        }

        return advert;
    }

}
