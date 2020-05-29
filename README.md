# Compilador Minipascal

### Objetivo
Criar um compilador para a dada linguagem. O projeto foi dividido em 5 etapas, sendo:
1. ANÁLISE LÉXICA
2. ANÁLISE SINTÁTICA
3. MONTAGEM E VISUALIZAÇÃO DA AST
4. ANÁLISE DE CONTEXTO
5. GERAÇÃO DE CÓDIGO

### Arquivos
| Arquivo | README |
| ------ | ------ |
| sintaxe_livre_contexto.txt | Sintaxe livre de contexto da linguagem Mini-Pascal disponibilizado pelo professor |
| Relatório Final.pdf| Todos os detalhes do compilador descritos, desde linguagem até geração de código |
| Programs| Pasta com todos os programas para testar |
|Compiler_v4| O código do compilador|

### Building Compiler
```sh
$ cd Compiler_v4/
$ make
```

### Building aplication using this compiler
```sh
$ cd Compiler_v4/
$ java -cp "bin/" Main -i ../Programs/6.mp
```

### Comandos
É possível controlar a execução do compilador, desativando etapas e analisando a saida. Para saber as opções:
```sh
$ java -cp "bin/" Main -h
```
