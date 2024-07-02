# Product Manager Application

## Introdução

Este projeto é uma extensão do [Product Manager Application (Extended)](https://github.com/Paloma-Regis-Ferreira/product-manager-exercicio-2.git), que por sua vez é uma extensão do [Product Manager Application original](https://github.com/Paloma-Regis-Ferreira/product-manager-exercicio-1.git). Ele utiliza a arquitetura limpa para garantir um código modular, de fácil manutenção e escalável. Nesta versão, foram adicionadas funcionalidades de mensageria utilizando RabbitMQ para monitoramento de eventos relacionados à criação de novos produtos.

## Alterações Realizadas

### Implementação de Mensageria com RabbitMQ

Foi implementado um sistema de mensageria utilizando RabbitMQ para lidar com eventos relacionados à criação de novos produtos. As principais alterações incluem a adição do Producer, Consumer e classe de configuração.

### Estrutura de Pastas Atualizada

A estrutura de pastas foi atualizada para incluir diretórios específicos relacionados à mensageria com RabbitMQ. Foram adicionados os pacotes `messaging` dentro de `adapters` e `application`, responsáveis pela implementação do consumidor (`CreateProductConsumer.java`) e produtor (`CreateProductProducer.java`) respectivamente.

### Uso do CloudAMQP

Para este projeto, a mensageria com RabbitMQ está configurada utilizando o serviço CloudAMQP.
