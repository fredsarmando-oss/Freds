package com.example.data.model

data class Lesson(
    val id: String,
    val title: String,
    val subtitle: String,
    val level: String, // "Iniciante", "Intermediário", "Avançado"
    val chapter: String,
    val readTime: String,
    val content: String,
    val iconName: String
)

object LessonsData {
    val lessons = listOf(
        // INICIANTE
        Lesson(
            id = "ini_1",
            title = "Ferramentas Básicas de Bancada",
            subtitle = "Equipe seu laboratório com o equipamento correto",
            level = "Iniciante",
            chapter = "Introdução & Preparação",
            readTime = "5 min",
            iconName = "construction",
            content = """
                ### 1. Ferramentas Essenciais para Começar
                Para iniciar na manutenção de celulares, você não precisa de equipamentos caríssimos, mas sim de ferramentas de precisão e qualidade. Uma boa bancada é limpa, organizada e antiestática.
                
                #### A. Jogo de Chaves de Precisão
                - **Torx (T2, T3, T4, T5, T6):** Muito usados em Motorola, Xiaomi e aparelhos mais antigos.
                - **Pentalobe (0.8, 1.2):** Exclusivas para a carcaça de iPhones.
                - **Tri-wing (Y000):** Usada internamente em iPhones mais novos (a partir do iPhone 7).
                - **Fenda e Philips de precisão:** O padrão geral da indústria.
                
                #### B. Espátulas e Palhetas de Plástico
                - **Importante:** Nunca use ferramentas metálicas diretamente em baterias ou perto de conectores flexíveis para evitar curtos ou perfurações perigosas. Use espátulas plásticas (Nylon Spudger) ou palhetas.
                
                #### C. Soprador Térmico ou Estação de Retrabalho (Ar Quente)
                - Essencial para amolecer a cola das tampas traseiras e telas de aparelhos modernos. Temperatura recomendada para abertura: **80°C a 100°C** para evitar queimar o display LCD/OLED ou danificar componentes plásticos.
                
                #### D. Álcool Isopropílico (99.9% de pureza)
                - É o único líquido permitido para limpeza de placas e componentes eletrônicos, pois não contém água e evapora quase instantaneamente, evitando oxidações.
                
                ---
                ### 2. Dicas de Ouro do Mestre Buba
                1. **Manta Antiestática (ESD):** Sempre trabalhe sobre uma manta de silicone antiestática conectada ao aterramento para proteger as placas de descargas do seu corpo.
                2. **Organização de Parafusos:** Use mantas magnéticas com marcações ou mapas para cada modelo. Colocar um parafuso maior no buraco errado (erro conhecido como "Long Screw Damage") pode perfurar as trilhas internas da placa e inutilizar o celular para sempre!
            """.trimIndent()
        ),
        Lesson(
            id = "ini_2",
            title = "Desmontagem com Segurança",
            subtitle = "Como abrir celulares sem danificar telas ou flex",
            level = "Iniciante",
            chapter = "Prática de Bancada",
            readTime = "7 min",
            iconName = "open_in_new",
            content = """
                ### 1. O Processo de Abertura
                A maioria dos aparelhos modernos possui traseira de vidro ou plástico colada e tela também colada no chassi.
                
                #### Passo 1: Aquecimento Controlado
                - Coloque o celular na separadora de LCD (separadora de calor) a **80°C por cerca de 3 a 5 minutos**, ou aplique ar quente com a estação de retrabalho com movimentos circulares constantes a cerca de 10cm de distância.
                - *Atenção:* Nunca concentre o calor em uma única área por muito tempo.
                
                #### Passo 2: Criando a Primeira Brecha
                - Use uma ventosa de silicone forte para puxar levemente a tampa traseira ou tela.
                - Assim que surgir um espaço milimétrico, insira uma palheta de plástico fina ou folha de acetato umedecida com uma gota de álcool isopropílico.
                
                #### Passo 3: Cortando a Cola
                - Deslize a palheta suavemente pelas bordas.
                - **Alerta de Perigo:** Conheça o posicionamento dos cabos flexíveis antes de cortar! Em aparelhos como iPhones, a tela abre para o lado (como um livro). Em aparelhos Samsung ou Motorola, há o flex da biometria traseira que se corta facilmente se a espátula entrar muito fundo.
                
                ---
                ### 2. A Regra de Ouro Nº 1: Desconectar a Bateria!
                Assim que você abrir o aparelho e remover as blindagens metálicas protetoras:
                - **O primeiro conector a ser desconectado SEMPRE deve ser a Bateria.**
                - Use uma espátula de plástico. Nunca use pinça de metal ou chaves perto do conector da bateria enquanto ela estiver ativa.
                - Se você mexer nas telas ou câmeras com a bateria conectada, causará um curto na linha do backlight (luz de fundo) ou danificará o processador.
            """.trimIndent()
        ),
        Lesson(
            id = "ini_3",
            title = "Substituição de Componentes Modulares",
            subtitle = "Troca de telas, baterias e conectores fáceis",
            level = "Iniciante",
            chapter = "Manutenção Prática",
            readTime = "6 min",
            iconName = "phonelink_setup",
            content = """
                ### 1. Peças Modulares
                São componentes que se conectam à placa principal por meio de conectores FPC (Flat Printed Circuit), sem necessidade de solda por ar ou ferro de soldar.
                
                #### A. Substituição de Bateria
                - Baterias modernas vêm coladas com fitas adesivas dupla face extremamente fortes.
                - **Nunca use espátulas metálicas ou pontiagudas para alavancar a bateria.** Se furar ou dobrar, ela pode explodir ou pegar fogo instantaneamente.
                - *Técnica Correta:* Aplique algumas gotas de álcool isopropílico por trás da bateria (ele dissolve a cola) e use uma espátula plástica larga ou puxe as abas de remoção rápida (pull-tabs).
                
                #### B. Troca de Tela (Display Completo)
                - Ao testar uma tela nova, faça-o **antes de colar**. Conecte provisoriamente, ligue o aparelho e teste o brilho, o touch em toda a superfície e as cores.
                - Remova toda a cola antiga do aro do chassi usando uma espátula de metal cega e escova de dentes com álcool isopropílico. Se sobrar fiapos ou pedacinhos de vidro, a tela nova quebrará facilmente ao ser pressionada ou colada.
                - Use cola específica de manutenção (B7000 - transparente ou T7000 - preta). Aplique uma camada fina ao redor do aro, aguarde 2 minutos para iniciar a secagem e encaixe a tela. Prenda com elásticos ou prendedores específicos por 2 a 4 horas.
            """.trimIndent()
        ),
        // INTERMEDIÁRIO
        Lesson(
            id = "int_1",
            title = "Diagnóstico com o Multímetro",
            subtitle = "Aprenda a testar componentes e encontrar caminhos rompidos",
            level = "Intermediário",
            chapter = "Eletrônica de Celulares",
            readTime = "8 min",
            iconName = "speed",
            content = """
                ### 1. Escalas Fundamentais para Celular
                O multímetro é os olhos do técnico de placas. Sem ele, você estará apenas adivinhando.
                
                #### A. Escala de Tensão Contínua (DCV - V⎓)
                - Configurado para medir baterias e tensões de alimentação.
                - *Como medir:* Ponta preta no terra (chassi metálico/blindagem) e ponta vermelha no ponto positivo. Uma bateria de celular saudável deve marcar entre **3.7V e 4.3V**. Se marcar abaixo de **3.0V**, o celular não terá força sequer para carregar e você precisará dar um "choque" ou ativação externa na bateria usando uma fonte de bancada.
                
                #### B. Escala de Continuidade (Bip - 🕭)
                - Usado para verificar conexões e encontrar curtos-circuitos diretos para o terra.
                - Se você encostar uma ponta no terra e a outra em um capacitor saudável, ele **não deve apitar** dos dois lados. Se apitar dos dois lados, a linha daquele capacitor está em curto-circuito!
                
                #### C. Condução Reversa (Escala de Diodo 🗲)
                - É a técnica mais importante para mapear falhas sob conectores FPC de tela, carga e botões.
                - *Como usar:* Ponta **Vermelha no Terra (GND)** e ponta **Preta no pino a ser testado** (Inversão de polaridade).
                - O multímetro injeta uma pequena corrente e mede a queda de tensão em milivolts (mV).
                - *Valores típicos:* Entre **300mV e 700mV**.
                - **OL (Open Line):** Linha aberta/rompida (trilha quebrada embaixo do processador ou filtro queimado).
                - **000mV:** Linha em curto-circuito total para o terra.
            """.trimIndent()
        ),
        Lesson(
            id = "int_2",
            title = "Soldagem e Troca de Conectores",
            subtitle = "Soldagem de conectores de carga micro-USB e Tipo-C",
            level = "Intermediário",
            chapter = "Soldagem de Precisão",
            readTime = "9 min",
            iconName = "waves",
            content = """
                ### 1. Equipamentos e Ajustes
                - **Ferro de Soldar:** Ponta faca ou ponta cônica fina de qualidade. Temperatura recomendada: **350°C a 380°C**.
                - **Soprador Térmico:** Temperatura de fusão da solda original sem chumbo (Lead-Free): **340°C a 360°C** com vazão de ar média/baixa para não soprar componentes vizinhos para longe.
                - **Insumos obrigatórios:** Fluxo de solda pastoso (no-clean), solda em fio de boa qualidade (liga 63/37 com chumbo, que derrete mais fácil a **183°C**) e malha dessoldadora de cobre.
                
                ---
                ### 2. Passo a Passo da Troca de Conector USB Tipo-C
                #### Passo 1: Proteção Térmica
                - Cubra todos os componentes plásticos, microfones ou conectores vizinhos com **fita Kapton** ou fita de alumínio grossa. O calor excessivo destrói microfones digitais facilmente.
                
                #### Passo 2: Remoção do Conector Velho
                - Aplique um pouco de fluxo de solda ao redor dos pinos de ancoragem.
                - Use a estação de ar quente a **350°C** em movimentos circulares sobre o conector.
                - Com uma pinça, puxe suavemente para cima assim que a solda brilhar (entrar em estado líquido). Nunca force a puxada para não arrancar as trilhas de dados (pads) da placa.
                
                #### Passo 3: Limpeza e Preparação dos Pads
                - Aplique fluxo e passe o ferro de soldar com um pouco de liga com chumbo para misturar e abaixar o ponto de fusão.
                - Use a malha dessoldadora para sugar a solda velha dos furos de ancoragem e das trilhas. Limpe tudo muito bem com álcool isopropílico.
                
                #### Passo 4: Soldagem do Novo Conector
                - Coloque o novo conector perfeitamente alinhado.
                - Solde primeiro as pernas de sustentação (ancoragem) com o ferro de soldar para fixar a peça física.
                - Aplique fluxo pastoso nas trilhas de comunicação e arraste a ponta do ferro bem limpa e estanhada sobre as trilhas traseiras. A solda subirá nos pinos por capilaridade. Use microscópio ou lupa para conferir se não há pontes de solda encostando um pino no outro.
            """.trimIndent()
        ),
        Lesson(
            id = "int_3",
            title = "Análise de Consumo na Fonte",
            subtitle = "Como usar a fonte de bancada de 4 dígitos para diagnosticar aparelhos mortos",
            level = "Intermediário",
            chapter = "Diagnóstico Avançado",
            readTime = "8 min",
            iconName = "settings_input_component",
            content = """
                ### 1. Configurando a Fonte de Bancada
                A fonte substitui a bateria do celular e nos permite ver exatamente quanta corrente (Amperes) o aparelho consome ao tentar ligar.
                - **Configuração padrão de segurança:** **4.2 Volts** (tensão média da bateria) e corrente limitada em **2.0 Amperes** (para não queimar a placa em caso de curto-circuito forte).
                - Use cabos de ativação específicos para a marca correspondente (iBoot / cabos jacaré).
                
                ---
                ### 2. Interpretando os Consumos de Corrente Comuns
                
                #### A. Consumo Antes de Pressionar o Botão Power (Consumo Primário)
                - Conectou o celular na fonte e o amperímetro já marcou consumo alto (ex: 1.5A ou a fonte desarmou apitando)?
                - **Diagnóstico:** Curto-circuito em uma linha primária de alimentação (VBAT ou VBUS). Um capacitor ou circuito integrado ligado diretamente à bateria está danificado. A placa esquentará muito nessa região.
                - Conectou e marcou consumo baixinho e fixo (ex: 0.01A a 0.05A)?
                - **Diagnóstico:** Fuga de corrente secundária ou primária leve.
                
                #### B. Consumo Após Pressionar o Botão Power (Consumo Secundário)
                - Pressionou o power e o consumo sobe para **0.05A (50mA) e cai a zero** ao soltar o botão?
                - **Diagnóstico:** Falha de comunicação ou ausência de inicialização. Geralmente PMIC (circuito de gerenciamento de energia) detecta uma linha secundária em curto ou o processador não está respondendo.
                - Pressionou o power e o consumo fica travado entre **0.08A e 0.12A (80mA - 120mA) fixos**?
                - **Diagnóstico:** Falha de software profunda, setor de Boot inicial ou falha na memória EMMC/UFS. Tente conectar ao computador para ver se reconhece o driver de recuperação (QDLoader, MTK USB Port, etc).
                - Consumo oscila normalmente de **0.20A a 1.20A**?
                - **Diagnóstico:** Comportamento normal de boot. O aparelho está ligando fisicamente, mas se não exibe imagem, o problema é na tela ou no circuito do backlight.
            """.trimIndent()
        ),
        // AVANÇADO
        Lesson(
            id = "adv_1",
            title = "Micro-soldagem e Reparos SMD/BGA",
            subtitle = "Técnicas de reballing e troca de circuitos integrados",
            level = "Avançado",
            chapter = "Micro-eletrônica",
            readTime = "10 min",
            iconName = "hardware",
            content = """
                ### 1. O que é Micro-soldagem?
                Diferente de trocar telas e cabos, a micro-soldagem lida com componentes menores que um grão de arroz e circuitos integrados (CIs) que possuem centenas de esferas de solda embaixo deles (encapsulamento BGA - Ball Grid Array).
                
                ---
                ### 2. O Processo de Reballing de Circuitos Integrados
                Reballing é o ato de remover um circuito integrado que está com solda fria ou quebrada, limpar toda a solda antiga e refazer as esferas de solda perfeitas sob o componente usando um estêncil de aço e solda em pasta.
                
                #### Passo A: Extração do Componente BGA
                - Aplique fluxo líquido ou pastoso de alta qualidade ao redor do CI.
                - Ajuste a estação de retrabalho para **360°C a 380°C** e vazão de ar em 3.
                - Faça calor focado e constante sobre o CI. Com uma pinça cirúrgica extremamente afiada nas mãos, dê toques leves na lateral do chip. Se ele se mover e voltar para o lugar (efeito mola devido à tensão superficial da solda líquida), ele está pronto para ser levantado. Puxe-o suavemente para cima.
                - *Cuidado:* Nunca levante antes que todas as esferas estejam derretidas para não arrancar as conexões da placa (pads).
                
                #### Passo B: Limpeza dos Pads na Placa
                - Coloque fluxo pastoso sobre os pinos remanescentes na placa.
                - Use uma malha dessoldadora de cobre de boa largura com a ponta faca do ferro de soldar bem paralela à placa. Mova sem pressionar para não arranhar a máscara de solda protetora verde. Limpe tudo com algodão ou cotonete umedecido em álcool isopropílico até que fique liso.
                
                #### Passo C: Refazendo as Esferas no Chip (Reballing)
                - Limpe as esferas antigas de baixo do CI usando o ferro de soldar.
                - Posicione o CI embaixo do estêncil de aço específico para aquele modelo, alinhando perfeitamente os furos. Use fita Kapton para segurar.
                - Aplique **solda em pasta** (liga contendo chumbo, fusão a 183°C) sobre os furos usando uma espátula, preenchendo todos os buraquinhos de forma homogênea. Retire o excesso com papel toalha.
                - Aproxime o soprador térmico a **300°C** com o fluxo de ar no mínimo (para não soprar as bolhas). Você verá a pasta cinza derreter instantaneamente e formar lindas esferas de solda brilhantes e perfeitamente calibradas!
                - Remova o CI do estêncil, aplique um pouco de fluxo e faça um leve calor para assentar as esferas.
            """.trimIndent()
        ),
        Lesson(
            id = "adv_2",
            title = "Interpretação de Esquemas Elétricos",
            subtitle = "Aprenda a rastrear linhas de dados e de alimentação principais",
            level = "Avançado",
            chapter = "Análise de Circuitos",
            readTime = "10 min",
            iconName = "map",
            content = """
                ### 1. Entendendo os Manuais de Serviço e Esquemas
                O esquema elétrico mostra a ligação lógica de todos os componentes na placa, enquanto o "Layout do Componente" mostra a localização física deles na placa de circuito impresso (PCB).
                - Usamos softwares profissionais de rastreamento (como ZXW, JCID, Borneo Schematics ou Pragmafix) para ver as trilhas de cobre internas da placa com apenas um clique.
                
                ---
                ### 2. As Linhas de Alimentação Principais
                O circuito integrado de carga e o PMIC dividem as tensões nas seguintes principais vias:
                
                #### A. VBUS
                - É a tensão que vem diretamente do conector de carga USB. Deve medir sempre **5.0V a 9.0V / 12.0V** (se usar carregador turbo/Power Delivery rápido).
                - Se não houver tensão na VBUS medida na placa principal, o cabo flex de carga intermediário ou o conector de carga está quebrado.
                
                #### B. VBAT (ou V_BATTERY)
                - É a linha de alta corrente ligada diretamente ao polo positivo da bateria do aparelho. Mede entre **3.7V e 4.2V**.
                - Qualquer curto na VBAT impede que o celular dê qualquer sinal de vida e faz o aparelho esquentar assim que se conecta a fonte de alimentação.
                
                #### C. VOUT / VPH_PWR / SYSTEM
                - É a linha principal do sistema criada pelo CI de Carga secundário (OVP/Charger). Ela alimenta quase todos os setores do celular e costuma ter a mesma tensão da bateria. Curtos nessa linha são comuns e desligam o celular totalmente.
                
                ---
                ### 3. Símbolos Comuns no Esquema
                - **C (Capacitor):** Filtra ruídos e armazena carga temporária. Liga uma extremidade na linha ativa e a outra no terra (GND). Capacitores ruins entram em curto internamente e derrubam as linhas de alimentação.
                - **R (Resistor):** Limita a corrente ou cria divisores de tensão. Fica conectado em série. Se queimar, abre o circuito.
                - **L (Bobina / Indutor):** Filtra correntes e trabalha em conjunto com CIs para elevar tensões (como no circuito de Backlight de telas).
                - **U (Circuito Integrado):** O chip processador, memória, PMIC, etc.
            """.trimIndent()
        ),
        Lesson(
            id = "adv_3",
            title = "Injeção de Tensão e Técnica do Breu",
            subtitle = "Localizando componentes microscópicos aquecidos em segundos",
            level = "Avançado",
            chapter = "Métodos de Diagnóstico",
            readTime = "9 min",
            iconName = "local_fire_department",
            content = """
                ### 1. O Desafio de Encontrar Curtos-Circuitos
                Quando uma linha primária como a VBAT está em curto, algum componente específico está conduzindo toda a corrente elétrica direto para o terra, transformando energia em calor intenso. Mas como achar um único capacitor defeituoso no meio de centenas na placa?
                
                ---
                ### 2. A Técnica do Breu (Fumaça de Resina)
                Esta é a técnica de detecção térmica mais barata, rápida e clássica do mundo, usada por profissionais em substituição a caras câmeras térmicas.
                
                #### Passo 1: Preparação do Breu
                - O breu é uma resina vegetal sólida de soldagem.
                - Coloque um pequeno pedaço de breu no bocal do seu ferro de soldar quente (**350°C**). Ele começará a evaporar, gerando uma fumaça branca espessa.
                - Passe a fumaça de breu lentamente sobre toda a superfície da placa eletrônica suspeita. A fumaça se condensará formando uma fina camada branca de "neve" fosca sobre todos os capacitores e circuitos integrados.
                
                #### Passo 2: Injeção de Tensão Segura
                - Configure sua fonte de bancada para a **tensão de trabalho daquela linha** (por exemplo: se injetar na linha VBAT, configure em **2.0V a 3.8V** - nunca ultrapasse a tensão nominal da linha sob o risco de queimar processadores saudáveis!).
                - Solde um pequeno fio de cobre na linha afetada (sobre um pad de capacitor conhecido) e conecte o jacaré positivo da fonte. O negativo vai em qualquer parte metálica do terra da placa.
                
                #### Passo 3: O Componente se Revela!
                - Ligue a fonte de alimentação.
                - O componente em curto esquentará quase instantaneamente devido à alta corrente elétrica.
                - **A fina camada branca de breu derreterá imediatamente sobre o componente culpado**, que voltará a ficar escuro e brilhante, enquanto o resto da placa continua coberto de breu branco!
                - Agora é só remover o capacitor defeituoso com o ferro de soldar ou soprador térmico e testar se o curto sumiu! Substitua-o por outro de mesmo valor extraído de uma placa sucata e o celular voltará à vida!
            """.trimIndent()
        )
    )
}
