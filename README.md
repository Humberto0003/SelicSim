# SelicSim

Aplicativo Android desenvolvido em **Kotlin**, utilizando **Jetpack Compose**, para simular investimentos com base na meta da taxa Selic divulgada pelo Banco Central do Brasil.

O aplicativo permite informar o valor inicial do investimento, o aporte mensal e o prazo, calculando o montante final, o total investido e os juros acumulados.

As simulações são armazenadas localmente e podem ser consultadas posteriormente.

## Funcionalidades

- **Simulador de investimentos:** calcula a evolução de um investimento utilizando juros compostos e aportes mensais.
- **Consulta da Selic:** obtém a meta da taxa Selic por meio da API do Banco Central.
- **Histórico de simulações:** armazena e consulta os resultados utilizando Room e SQLite.
- **Navegação:** permite alternar entre a calculadora e o histórico.

## Tecnologias utilizadas

- **Kotlin:** linguagem de programação.
- **Jetpack Compose:** construção das interfaces.
- **Retrofit:** consumo da API externa.
- **Moshi:** conversão de respostas JSON para objetos Kotlin.
- **Room:** persistência local de dados.
- **SQLite:** banco de dados local.
- **Coroutines:** execução assíncrona das consultas à API e das operações no banco.
- **Navigation Compose:** navegação entre as telas.

## Estrutura do projeto

```text
br.edu.ifal.selicsim/
├── api/
│   ├── RetrofitHelper.kt
│   └── SelicApi.kt
├── db/
│   ├── DatabaseHelper.kt
│   └── SimulationDao.kt
├── domain/
│   ├── InvestmentCalculator.kt
│   ├── Selic.kt
│   ├── SimulationEntity.kt
│   └── SimulationResult.kt
├── navigation/
│   └── AppNavigation.kt
├── ui/
│   ├── screen/
│   │   ├── SelicCalculatorScreen.kt
│   │   └── SimulationHistoryScreen.kt
│   └── theme/
└── MainActivity.kt
```

## API utilizada

O aplicativo utiliza a API de séries temporais (SGS) do Banco Central do Brasil.

**Série:** 432 — Meta da taxa Selic.

**Endpoint:**

`https://api.bcb.gov.br/dados/serie/bcdata.sgs.432/dados/ultimos/1?formato=json`

A taxa obtida é utilizada como referência anual para os cálculos do simulador.

## Cálculo do investimento

A taxa anual é convertida em uma taxa mensal equivalente:

\[
i_m = (1+i_a)^{1/12}-1
\]

O saldo é atualizado mensalmente com a aplicação dos juros e a adição do aporte:

\[
S_m = S_{m-1}(1+i_m)+A
\]

Onde:

- \(i_m\): taxa mensal.
- \(i_a\): taxa anual em formato decimal.
- \(S_m\): saldo ao final do mês.
- \(A\): aporte mensal.

## Banco de dados

O aplicativo utiliza Room para armazenar as simulações na tabela `simulations`.

Cada registro contém os valores informados, a taxa utilizada, os resultados calculados e a data da simulação.

As operações são realizadas por meio do `SimulationDao`, utilizando funções `suspend`.

## Execução

1. Clone o repositório.
2. Abra o projeto no Android Studio.
3. Aguarde a sincronização do Gradle.
4. Inicie um emulador Android ou conecte um dispositivo físico.
5. Execute o módulo `app`.

## Projeto acadêmico

Projeto desenvolvido para a disciplina de Programação para Dispositivos Móveis do Instituto Federal de Alagoas (IFAL).

O desenvolvimento contempla interfaces com Jetpack Compose, consumo de API externa com Retrofit, persistência local com Room e operações assíncronas utilizando Coroutines.
