package com.nservices.mypet.jpa;

import com.nservices.mypet.dto.RegistryInfoDTO;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.repository.PetStateInfoRepository;
import com.nservices.mypet.service.RegistryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class JpaPetStateInfoTest {

    @Autowired
    private RegistryService registryService;

    @Autowired
    private PetStateInfoRepository petStateInfoRepository;

    @Test
    public void testPetStateInfoFindAllByUsername() {
        RegistryInfoDTO registryInfoDTO = new RegistryInfoDTO();
        registryInfoDTO.setUsername("Nik_123");
        registryInfoDTO.setPetName("Umka");
        registryInfoDTO.setEmail("nik@gmail.com");
        registryInfoDTO.setPassword("1");
        registryInfoDTO.setOwnerName("Nik");
        registryInfoDTO.setOwnerAge(41);

        registryService.registerPetAndUser(registryInfoDTO);
        List<PetStateInfoEntity> petStateInfo = petStateInfoRepository.findAllByUsername(registryInfoDTO.getUsername());
        petStateInfo.forEach(System.out::println);
    }


}
