package com.example.data.model

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

object QuizQuestions {
    val levelQuestions = mapOf(
        "Iniciante" to listOf(
            QuizQuestion(
                id = "q_ini_1",
                question = "Qual é o primeiro procedimento obrigatório após abrir qualquer celular e remover as blindagens?",
                options = listOf(
                    "Trocar a tela imediatamente",
                    "Desconectar o cabo flexível da bateria",
                    "Limpar a placa com álcool de cozinha comum",
                    "Conectar o carregador turbo para testar"
                ),
                correctAnswerIndex = 1,
                explanation = "A bateria deve ser SEMPRE desconectada primeiro para remover a energia de toda a placa, prevenindo curtos-circuitos acidentais ao manusear componentes metálicos ou outros flex."
            ),
            QuizQuestion(
                id = "q_ini_2",
                question = "Qual líquido é o único recomendado para fazer limpeza química de placas de celulares?",
                options = listOf(
                    "Água destilada",
                    "Álcool Isopropílico (99.9% de pureza)",
                    "Detergente neutro líquido",
                    "Acetona doméstica"
                ),
                correctAnswerIndex = 1,
                explanation = "O Álcool Isopropílico possui quase zero por cento de água (pureza de 99.9%), evaporando rapidamente sem deixar resíduos ou oxidações na placa do celular."
            ),
            QuizQuestion(
                id = "q_ini_3",
                question = "O que significa 'Long Screw Damage' na manutenção de celulares?",
                options = listOf(
                    "Parafusar com muita força e espanar a rosca externa",
                    "Danos graves nas trilhas internas da placa devido ao uso de um parafuso maior no buraco errado",
                    "Esquecer de colocar os parafusos na carcaça do celular",
                    "Usar parafusadeiras elétricas automáticas de alta velocidade"
                ),
                correctAnswerIndex = 1,
                explanation = "Long Screw Damage ocorre quando o técnico coloca um parafuso mais longo em um orifício destinado a um menor. Ao apertar, o parafuso perfura as microtrilhas de cobre internas da placa-mãe, danificando o celular permanentemente."
            ),
            QuizQuestion(
                id = "q_ini_4",
                question = "A que temperatura aproximada recomendada deve-se ajustar a separadora ou soprador para descolar tampas de vidro traseiras com segurança?",
                options = listOf(
                    "Aproximadamente 400°C",
                    "Entre 80°C e 100°C",
                    "Aproximadamente 250°C",
                    "0°C (congelamento por spray)"
                ),
                correctAnswerIndex = 1,
                explanation = "Temperaturas de 80°C a 100°C são suficientes para amolecer a cola traseira sem danificar os componentes plásticos, baterias de lítio ou o display LCD/OLED."
            ),
            QuizQuestion(
                id = "q_ini_5",
                question = "Por que nunca devemos usar espátulas de metal diretamente em baterias de celular?",
                options = listOf(
                    "Porque a espátula pode arranhar o logotipo da marca",
                    "Porque o metal faz a bateria perder a garantia",
                    "Porque ferramentas metálicas pontiagudas podem perfurar a bateria de lítio, causando curto-circuito, fumaça tóxica e chamas instantâneas",
                    "Porque as ferramentas metálicas são muito pesadas"
                ),
                correctAnswerIndex = 2,
                explanation = "As baterias de íon de lítio são altamente instáveis se dobradas ou perfuradas. O contato de metal com metal do miolo interno gera faíscas internas fortes e combustão térmica rápida."
            )
        ),
        "Intermediário" to listOf(
            QuizQuestion(
                id = "q_int_1",
                question = "Para testar se uma bateria de celular possui carga mínima para conseguir dar partida (boot), qual escala do multímetro deve ser usada?",
                options = listOf(
                    "Escala de Corrente Alternada (ACV)",
                    "Escala de Tensão Contínua (DCV - V⎓)",
                    "Escala de Resistência (Ω) em 20M",
                    "Escala de Capacitância (F)"
                ),
                correctAnswerIndex = 1,
                explanation = "A bateria do celular fornece corrente contínua (DC), por isso medimos em Tensão Contínua. Baterias saudáveis marcam de 3.7V a 4.3V. Abaixo de 3.0V ela precisará de reativação."
            ),
            QuizQuestion(
                id = "q_int_2",
                question = "Na técnica de Condução Reversa (teste de queda de tensão em FPC), qual ponteira do multímetro deve ir para o terra (blindagem/GND)?",
                options = listOf(
                    "A ponteira Vermelha (Red) no terra",
                    "A ponteira Preta (Black) no terra",
                    "Ambas as ponteiras devem ser conectadas juntas no terra",
                    "Nenhuma ponteira vai ao terra, medimos em série"
                ),
                correctAnswerIndex = 0,
                explanation = "Na condução reversa, a ponteira Vermelha vai para o Terra (GND) e a ponteira Preta sonda os pinos do conector FPC. Isso faz com que a corrente flua no sentido inverso pelos diodos internos de proteção dos circuitos."
            ),
            QuizQuestion(
                id = "q_int_3",
                question = "Ao conectar um celular na fonte de bancada ajustada em 4.2V, o amperímetro já marca consumo alto (ex: 1.2A) imediatamente ANTES de apertar o botão power. Qual é o diagnóstico?",
                options = listOf(
                    "O celular está carregando normalmente",
                    "Há um curto-circuito em alguma linha de alimentação primária (ex: VBAT ou VBUS)",
                    "O botão de liga/desliga está quebrado em circuito aberto",
                    "O software do celular está corrompido"
                ),
                correctAnswerIndex = 1,
                explanation = "Se há consumo de corrente imediato sem que o botão power seja pressionado, significa que a energia está fluindo direto do conector para o terra devido a um componente em curto-circuito em uma linha primária."
            ),
            QuizQuestion(
                id = "q_int_4",
                question = "Para substituir um conector de carga Tipo-C usando soprador de ar quente, por que usamos fluxo de solda e fita Kapton?",
                options = listOf(
                    "O fluxo limpa a sujeira do conector e a fita Kapton serve para prender o cabo do ferro",
                    "O fluxo diminui a fusão da solda e a fita Kapton protege os microfones e peças plásticas vizinhas contra o calor excessivo",
                    "O fluxo serve para colar o conector e a fita Kapton serve para isolar a energia da tomada",
                    "Para dar brilho estético ao conector e fita para segurar o celular"
                ),
                correctAnswerIndex = 1,
                explanation = "O fluxo de solda auxilia na transferência térmica rápida e fusão limpa da solda. A fita Kapton suporta temperaturas extremas sem queimar, blindando microfones delicados de receberem calor."
            ),
            QuizQuestion(
                id = "q_int_5",
                question = "Você pressiona o botão power e a fonte de bancada de 4 dígitos sobe o consumo até 0.08A (80mA), ficando travada fixamente sem oscilar. Qual o problema provável?",
                options = listOf(
                    "Falha física de tela LCD queimada",
                    "Falha de Software (Bootloader ou Memória EMMC/UFS travada)",
                    "Curto total primário na linha de carga",
                    "Bateria com defeito de fábrica"
                ),
                correctAnswerIndex = 1,
                explanation = "Consumos fixos e baixos (geralmente entre 70mA e 150mA) após apertar o power indicam que a CPU acordou, mas travou na comunicação inicial com a memória RAM/Flash (falha de boot/software/solda fria no chip de memória)."
            )
        ),
        "Avançado" to listOf(
            QuizQuestion(
                id = "q_adv_1",
                question = "O que é 'Reballing' de um Circuito Integrado BGA?",
                options = listOf(
                    "Trocar a carcaça de alumínio externa do chip",
                    "O processo de remover o chip, limpar as esferas de solda antigas da placa e refazer novas esferas de solda perfeitas sob o chip usando um estêncil de metal",
                    "Aumentar o brilho da tela usando atalhos de hardware",
                    "Soldar fios finos de um conector até a bateria"
                ),
                correctAnswerIndex = 1,
                explanation = "Reballing reconstrói todas as microesferas de solda (balls) embaixo de chips BGA utilizando um estêncil, fluxo de solda e solda em pasta, solucionando problemas de conexões quebradas sob chips."
            ),
            QuizQuestion(
                id = "q_adv_2",
                question = "Na leitura de esquemas elétricos, o que representam as siglas VBUS, VBAT e VPH_PWR/SYSTEM?",
                options = listOf(
                    "São marcas de baterias importadas de alta performance",
                    "Diferentes barramentos e linhas principais de tensões geradas pelo circuito de carregamento e bateria",
                    "Protocolos de comunicação sem fio de alta frequência",
                    "Componentes químicos de soldas em pasta"
                ),
                correctAnswerIndex = 1,
                explanation = "VBUS é a tensão do carregador USB (5V+). VBAT é a tensão da bateria. VPH_PWR ou SYSTEM é a linha secundária gerada para alimentar os principais setores do celular."
            ),
            QuizQuestion(
                id = "q_adv_3",
                question = "Qual é o principal objetivo da técnica do Breu (Fumaça de Resina) na análise de placas de celular?",
                options = listOf(
                    "Criar uma camada protetora contra água e humidade",
                    "Revelar falhas de fiação nas antenas coaxiais",
                    "Isolar os pinos metálicos para evitar curto-circuito na montagem",
                    "Cobrir os componentes de fumaça branca e, ao injetar tensão, ver qual componente derrete a camada branca devido ao superaquecimento instantâneo"
                ),
                correctAnswerIndex = 3,
                explanation = "O breu derrete rapidamente sob temperaturas baixas. Ao cobrir a placa com a fumaça fosca e injetar tensão, o calor gerado pelo componente defeituoso derrete o breu sobre ele instantaneamente, revelando o culpado exato."
            ),
            QuizQuestion(
                id = "q_adv_4",
                question = "Se um capacitor na linha secundária de alimentação do circuito integrado estiver em curto-circuito total para o terra, o que acontece com a linha ativa?",
                options = listOf(
                    "A tensão da linha dobra de valor instantaneamente",
                    "A linha inteira é aterrada (GND), caindo sua resistência a 0 ohms e impedindo o circuito de funcionar",
                    "O capacitor passará a carregar duas vezes mais rápido",
                    "A bateria enviará energia sem fio para compensar"
                ),
                correctAnswerIndex = 1,
                explanation = "Como o capacitor está em curto interno, ele cria uma ponte direta entre a linha positiva e o terra (GND), fazendo com que toda a energia da linha vá para o terra, derrubando o circuito inteiro e impedindo o funcionamento."
            ),
            QuizQuestion(
                id = "q_adv_5",
                question = "Qual cuidado crucial de segurança deve ser tomado ao injetar tensão em uma linha de dados ou de baixa tensão secundária (ex: 1.8V)?",
                options = listOf(
                    "Injetar 5.0V para testar se os componentes suportam tensões industriais",
                    "Ajustar a tensão da fonte para que seja estritamente igual ou inferior à tensão nominal recomendada daquela linha no esquema",
                    "Usar luvas de soldador de alta tensão de borracha grossa",
                    "Injetar tensão alternada de tomada diretamente na trilha"
                ),
                correctAnswerIndex = 1,
                explanation = "Injetar tensões maiores do que o limite nominal da linha (por exemplo, injetar 4.0V em uma linha sensível de 1.2V ou 1.8V de dados da CPU) pode queimar chips saudáveis, especialmente o processador, inutilizando a placa de vez."
            )
        )
    )
}
