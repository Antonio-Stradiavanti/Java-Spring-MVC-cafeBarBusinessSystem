package ru.manannikov.cafeBarBusinessSystem.dto;

// Назвначение Dto классов -> передача данных от сервера к клиенту, они обеспечивают правильное форматирование JSON, HTML
public record ProductDto (
   String name,
   String description
)
{}
