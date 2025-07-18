package br.com.curso_aws_project02.controller;

import br.com.curso_aws_project02.model.ProductEventLogDto;
import br.com.curso_aws_project02.repository.ProductEventLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/events")
public class ProductEventLogController {

    private final ProductEventLogRepository productEventLogRepository;

    public ProductEventLogController(ProductEventLogRepository productEventLogRepository) {
        this.productEventLogRepository = productEventLogRepository;
    }

    @GetMapping
    public List<ProductEventLogDto> getAllEvents() {

        return StreamSupport
                .stream(productEventLogRepository.findAll().spliterator(), false)
                .map(ProductEventLogDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{code}")
    public List<ProductEventLogDto> findByCode(@PathVariable String code) {

        return productEventLogRepository.findAllByPk(code)
                .stream()
                .map(ProductEventLogDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{code}/{event}")
    public List<ProductEventLogDto> findByCodeAndEventType(@PathVariable String code, @PathVariable String event) {

        return productEventLogRepository.findAllByPkAndSkStartsWith(code, event)
                .stream()
                .map(ProductEventLogDto::new)
                .collect(Collectors.toList());
    }
}

















