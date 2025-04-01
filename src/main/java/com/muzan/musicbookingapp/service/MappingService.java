package com.muzan.musicbookingapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingService {

    private final ModelMapper modelMapper;

    public MappingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <D, E> D mapToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <D, E> E mapToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public <D, E> List<D> mapToDtoList(List<E> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> mapToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public <D, E> List<E> mapToEntityList(List<D> dtoList, Class<E> entityClass) {
        return dtoList.stream()
                .map(dto -> mapToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }

    public void mapProperties(Object source, Object target) {
        modelMapper.map(source, target);
    }
}