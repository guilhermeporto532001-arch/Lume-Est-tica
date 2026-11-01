package com.example.data

import com.example.model.*

object SampleData {
    val initialClients = listOf(
        Client(
            id = "c1",
            name = "Mariana Silveira",
            initials = "MS",
            phone = "(11) 98765-4321",
            email = "mariana.silveira@email.com",
            birthDate = "15/04/1992",
            cpf = "342.890.128-44",
            address = "Av. Paulista, 1500 - Jardins, SP",
            contactPreference = "WhatsApp",
            status = "Ativa",
            notes = "Pele sensível com tendência a rosácea leve na região malar. Prefere produtos sem fragrância.",
            photoConsent = true,
            lastAppointmentDate = "24/10/2024",
            nextAppointmentDate = "08/11/2024",
            totalSpent = 3850.0,
            vipTier = "Champagne VIP"
        ),
        Client(
            id = "c2",
            name = "Juliana Castro",
            initials = "JC",
            phone = "(11) 99123-5566",
            email = "juliana.castro@email.com",
            birthDate = "28/08/1988",
            cpf = "219.743.908-12",
            address = "Rua Oscar Freire, 820 - Cerqueira César, SP",
            contactPreference = "WhatsApp",
            status = "Ativa",
            notes = "Em protocolo de estímulo de colágeno e hidratação profunda. Excelente cicatrização.",
            photoConsent = true,
            lastAppointmentDate = "24/10/2024",
            nextAppointmentDate = "14/11/2024",
            totalSpent = 7400.0,
            vipTier = "Champagne VIP"
        ),
        Client(
            id = "c3",
            name = "Amanda Souza",
            initials = "AS",
            phone = "(11) 97654-8899",
            email = "amanda.souza@email.com",
            birthDate = "03/12/1995",
            cpf = "401.552.883-99",
            address = "Rua Bela Cintra, 1100 - Consolação, SP",
            contactPreference = "WhatsApp",
            status = "Ativa",
            notes = "Preparo pré-laser concluído com antioxidantes tópicos.",
            photoConsent = true,
            lastAppointmentDate = "24/10/2024",
            nextAppointmentDate = "28/11/2024",
            totalSpent = 2900.0,
            vipTier = "Gold"
        ),
        Client(
            id = "c4",
            name = "Beatriz Oliveira",
            initials = "BO",
            phone = "(11) 98222-1133",
            email = "beatriz.oliveira@email.com",
            birthDate = "19/06/1984",
            cpf = "154.992.341-77",
            address = "Al. Lorena, 600 - Jardins, SP",
            contactPreference = "WhatsApp",
            status = "Ativa",
            notes = "Queixa de perda de contorno mandibular. Indicado bioestimulador.",
            photoConsent = true,
            lastAppointmentDate = "18/10/2024",
            nextAppointmentDate = "25/10/2024",
            totalSpent = 5200.0,
            vipTier = "Gold"
        ),
        Client(
            id = "c5",
            name = "Carolina Mendes",
            initials = "CM",
            phone = "(11) 99444-7788",
            email = "carolina.mendes@email.com",
            birthDate = "22/01/1990",
            cpf = "388.210.985-02",
            address = "Rua Haddock Lobo, 400 - Cerqueira César, SP",
            contactPreference = "E-mail",
            status = "Retorno pendente",
            notes = "Pós-procedimento de peeling químico há 15 dias. Agendar revisão.",
            photoConsent = true,
            lastAppointmentDate = "09/10/2024",
            nextAppointmentDate = "26/10/2024",
            totalSpent = 1950.0,
            vipTier = "Standard"
        ),
        Client(
            id = "c6",
            name = "Fernanda Rocha",
            initials = "FR",
            phone = "(11) 98111-9900",
            email = "fernanda.rocha@email.com",
            birthDate = "14/05/1993",
            cpf = "433.881.002-33",
            address = "Rua Pamplona, 950 - Jardim Paulista, SP",
            contactPreference = "WhatsApp",
            status = "Ativa",
            notes = "Pacote corporal drenagem relaxante semanal.",
            photoConsent = false,
            lastAppointmentDate = "21/10/2024",
            nextAppointmentDate = "28/10/2024",
            totalSpent = 2400.0,
            vipTier = "Standard"
        )
    )

    val initialAppointments = listOf(
        Appointment(
            id = "a1",
            clientId = "c1",
            clientName = "Mariana Silveira",
            clientInitials = "MS",
            procedureName = "Limpeza com Ledterapia",
            doctorName = "Dra. Mariana",
            date = "21/10/2024",
            time = "10:30",
            room = "Sala 02",
            durationMinutes = 75,
            price = 380.0,
            status = AppointmentStatus.EM_ANDAMENTO,
            paymentMethod = "PIX",
            notes = "Higienização profunda, extração suave e banho de luz LED âmbar calmante."
        ),
        Appointment(
            id = "a2",
            clientId = "c2",
            clientName = "Juliana Castro",
            clientInitials = "JC",
            procedureName = "Protocolo Glow Rejuvenescimento",
            doctorName = "Dra. Mariana",
            date = "21/10/2024",
            time = "14:30",
            room = "Sala 01",
            durationMinutes = 60,
            price = 850.0,
            status = AppointmentStatus.CONFIRMADO,
            paymentMethod = "Cartão de Crédito",
            notes = "Sessão 3 do pacote. Foco em estímulo de luminosidade e renovação epidérmica."
        ),
        Appointment(
            id = "a3",
            clientId = "c3",
            clientName = "Amanda Souza",
            clientInitials = "AS",
            procedureName = "Laser Lavieen Fracionado",
            doctorName = "Dra. Mariana",
            date = "21/10/2024",
            time = "16:30",
            room = "Sala Laser",
            durationMinutes = 45,
            price = 1200.0,
            status = AppointmentStatus.CONFIRMADO,
            paymentMethod = "PIX",
            notes = "Anestésico tópico aplicado 30min antes. Protocolo BB Laser pele de porcelana."
        ),
        Appointment(
            id = "a4",
            clientId = "c4",
            clientName = "Beatriz Oliveira",
            clientInitials = "BO",
            procedureName = "Bioestimulador Radiesse",
            doctorName = "Dra. Mariana",
            date = "22/10/2024",
            time = "11:00",
            room = "Sala 01",
            durationMinutes = 90,
            price = 2600.0,
            status = AppointmentStatus.CONFIRMADO,
            paymentMethod = "Cartão de Crédito (3x)",
            notes = "Vetorização de terço inferior e ângulo de mandíbula."
        ),
        Appointment(
            id = "a5",
            clientId = "c5",
            clientName = "Carolina Mendes",
            clientInitials = "CM",
            procedureName = "Retorno & Avaliação Pós-Peeling",
            doctorName = "Dra. Mariana",
            date = "23/10/2024",
            time = "09:30",
            room = "Consultório 01",
            durationMinutes = 30,
            price = 0.0,
            status = AppointmentStatus.AGUARDANDO,
            paymentMethod = "Cortesia / Retorno",
            notes = "Avaliação de regeneração epitelial e prescrição de home care de manutenção."
        )
    )

    val initialProcedures = listOf(
        Procedure(
            id = "p1",
            name = "Limpeza com Ledterapia",
            category = "Facial",
            durationMinutes = 75,
            price = 380.0,
            description = "Higienização profunda dos poros com emoliente morno, extração sem marcas, vapor de ozônio, alta frequência e fototerapia LED calmante.",
            contraindications = "Lesões ativas de herpes, dermatite de contato aguda.",
            postCare = "Evitar maquiagem nas primeiras 12 horas. Uso rigoroso de protetor solar FPS 50+.",
            popular = true
        ),
        Procedure(
            id = "p2",
            name = "Protocolo Glow Rejuvenescimento",
            category = "Facial",
            durationMinutes = 60,
            price = 850.0,
            description = "Associação exclusiva de microinfusão de ativos biomiméticos com ácido hialurônico de baixo peso e peptídeos tensores.",
            contraindications = "Gestantes e lactantes, infecções cutâneas ativas.",
            postCare = "Manter hidratação intensa com sérum calmante por 48 horas.",
            popular = true
        ),
        Procedure(
            id = "p3",
            name = "Laser Lavieen Fracionado",
            category = "Laser & Tecnologia",
            durationMinutes = 45,
            price = 1200.0,
            description = "Tecnologia Thulium 1927nm para efeito BB Cream permanente, refinamento de poros, clareamento suave e viço instantâneo.",
            contraindications = "Pele bronzeada recente, uso de isotretinoína nos últimos 6 meses.",
            postCare = "Sensação de areia suave por 3 dias. Hidratação com água termal e reparador de barreira.",
            popular = true
        ),
        Procedure(
            id = "p4",
            name = "Bioestimulador Radiesse Duo",
            category = "Injetáveis",
            durationMinutes = 90,
            price = 2600.0,
            description = "Hidroxiapatita de cálcio para neocolegênese profunda, melhora da flacidez e sustentação tecidual com naturalidade.",
            contraindications = "Doenças autoimunes ativas, infecção local.",
            postCare = "Massagem suave 5x ao dia por 5 dias (regra dos 5). Evitar atividade física intensa por 48h.",
            popular = true
        ),
        Procedure(
            id = "p5",
            name = "Ultraformer MPT Full Face",
            category = "Laser & Tecnologia",
            durationMinutes = 90,
            price = 3200.0,
            description = "Ultrassom micro e macrofocado de última geração com micropulsos para ancoragem muscular e efeito lifting não cirúrgico.",
            contraindications = "Preenchimentos definitivos prévios (PMMA), feridas abertas.",
            postCare = "Edema leve temporário. Vida normal imediatamente após a sessão.",
            popular = true
        ),
        Procedure(
            id = "p6",
            name = "Drenagem Método Renata França",
            category = "Corporal",
            durationMinutes = 60,
            price = 280.0,
            description = "Manobras diferenciadas com pressão firme e ritmo acelerado que reduzem edemas corporais e definem curvas.",
            contraindications = "Trombose ativa, insuficiência cardíaca descompensada.",
            postCare = "Ingestão hídrica abundante ao longo do dia.",
            popular = false
        )
    )

    val initialPackages = listOf(
        PackageItem(
            id = "pkg1",
            clientId = "c2",
            clientName = "Juliana Castro",
            title = "Pacote Glow Rejuvenescimento",
            procedureName = "Protocolo Glow",
            totalSessions = 5,
            completedSessions = 3,
            price = 3600.0,
            expiryDate = "20/12/2024",
            status = "Em andamento"
        ),
        PackageItem(
            id = "pkg2",
            clientId = "c1",
            clientName = "Mariana Silveira",
            title = "Plano Limpeza & Fotobiomodulação",
            procedureName = "Limpeza com Ledterapia",
            totalSessions = 4,
            completedSessions = 2,
            price = 1350.0,
            expiryDate = "15/01/2025",
            status = "Em andamento"
        ),
        PackageItem(
            id = "pkg3",
            clientId = "c6",
            clientName = "Fernanda Rocha",
            title = "Combo Drenagem Corporal 10x",
            procedureName = "Drenagem Renata França",
            totalSessions = 10,
            completedSessions = 8,
            price = 2400.0,
            expiryDate = "10/11/2024",
            status = "Em andamento"
        )
    )

    val initialAnamneses = listOf(
        AnamneseForm(
            id = "an1",
            clientId = "c1",
            clientName = "Mariana Silveira",
            date = "20/10/2024",
            mainComplaint = "Sensação de poros dilatados e vermelhidão facial ao calor",
            aestheticGoals = "Uniformizar a textura e controlar a reatividade da pele",
            allergies = "Dipirona e ácido acetilsalicílico",
            medications = "Suplementação de Vitamina D e ômega 3",
            healthHistory = "Sem comorbidades sistêmicas. Sem diabetes ou hipertensão.",
            previousSurgeries = "Rinoplastia estética em 2021",
            pregnancyOrLactation = false,
            fitzpatrickSkinType = "II",
            skinSensitivity = "Muito Reativa",
            hasMelasmaOrAcne = false,
            confirmedContraindications = true,
            signatureConfirmed = true
        ),
        AnamneseForm(
            id = "an2",
            clientId = "c2",
            clientName = "Juliana Castro",
            date = "15/09/2024",
            mainComplaint = "Linhas finas perioculares e perda do brilho natural",
            aestheticGoals = "Lifting suave e estímulo de colágeno biológico sem volumização",
            allergies = "Nenhuma alergia relatada",
            medications = "Anticoncepcional oral",
            healthHistory = "Nega patologias crônicas. Exames recentes normais.",
            previousSurgeries = "Nenhuma",
            pregnancyOrLactation = false,
            fitzpatrickSkinType = "III",
            skinSensitivity = "Normal",
            hasMelasmaOrAcne = false,
            confirmedContraindications = true,
            signatureConfirmed = true
        )
    )

    val initialClinicalRecords = listOf(
        ClinicalRecord(
            id = "cr1",
            appointmentId = "a1",
            clientId = "c1",
            clientName = "Mariana Silveira",
            procedureName = "Limpeza com Ledterapia",
            date = "21/10/2024",
            time = "10:30",
            treatedArea = "Face completa, foco em região T e mento",
            productsUsed = "Emoliente Biotecnológico Dermatus, LED Âmbar 590nm, Sérum Calmante Pantenol",
            batchNumber = "LT-2024-998B",
            technicalParams = "LED Âmbar contínuo 15 minutos, dose 4.5 J/cm²",
            clinicalNotes = "Pele respondeu com excelente acalmia após fotobiomodulação. Sem marcas de extração.",
            postProcedureCare = "Manter uso de água termal gelada e protetor solar físico por 3 dias.",
            returnRecommendationDays = 30
        )
    )

    val initialTransactions = listOf(
        FinancialTransaction(
            id = "t1",
            clientName = "Mariana Silveira",
            description = "Limpeza com Ledterapia",
            date = "21/10/2024",
            amount = 380.0,
            isIncome = true,
            status = "Recebido",
            paymentMethod = "PIX"
        ),
        FinancialTransaction(
            id = "t2",
            clientName = "Juliana Castro",
            description = "Pacote Glow Rejuvenescimento",
            date = "21/10/2024",
            amount = 3600.0,
            isIncome = true,
            status = "Recebido",
            paymentMethod = "Cartão de Crédito 3x"
        ),
        FinancialTransaction(
            id = "t3",
            clientName = "Amanda Souza",
            description = "Laser Lavieen Fracionado",
            date = "21/10/2024",
            amount = 1200.0,
            isIncome = true,
            status = "Pendente",
            paymentMethod = "PIX na recepção"
        ),
        FinancialTransaction(
            id = "t4",
            clientName = "Fornecedor Allergan",
            description = "Reposição Toxina Botulínica Lote #TB24",
            date = "18/10/2024",
            amount = 4500.0,
            isIncome = false,
            status = "Pago",
            paymentMethod = "Transferência Bancária"
        ),
        FinancialTransaction(
            id = "t5",
            clientName = "Beatriz Oliveira",
            description = "Sessão Bioestimulador Radiesse",
            date = "22/10/2024",
            amount = 2600.0,
            isIncome = true,
            status = "Pendente",
            paymentMethod = "Cartão de Crédito"
        )
    )

    val initialInventory = listOf(
        InventoryItem(
            id = "inv1",
            name = "Toxina Botulínica 100U",
            category = "Injetáveis",
            quantity = 8,
            unit = "frascos",
            batchLot = "TB-2024-88A",
            expiryDate = "12/2025",
            minThreshold = 3,
            unitCost = 850.0,
            supplier = "Allergan Aesthetics"
        ),
        InventoryItem(
            id = "inv2",
            name = "Ácido Hialurônico Ultra Deep 1ml",
            category = "Injetáveis",
            quantity = 2,
            unit = "seringas",
            batchLot = "AH-99120",
            expiryDate = "08/2025",
            minThreshold = 4,
            unitCost = 540.0,
            supplier = "Juvederm / Galderma"
        ),
        InventoryItem(
            id = "inv3",
            name = "Radiesse (+) 1.5ml Hidroxiapatita",
            category = "Bioestimuladores",
            quantity = 5,
            unit = "seringas",
            batchLot = "RD-4431-B",
            expiryDate = "04/2026",
            minThreshold = 2,
            unitCost = 1100.0,
            supplier = "Merz Aesthetics"
        ),
        InventoryItem(
            id = "inv4",
            name = "Anestésico Tópico Lidocaína 7% 30g",
            category = "Tópicos",
            quantity = 12,
            unit = "bisnagas",
            batchLot = "LIDO-2024-03",
            expiryDate = "02/2025",
            minThreshold = 5,
            unitCost = 65.0,
            supplier = "Farmácia Magistral Lume"
        ),
        InventoryItem(
            id = "inv5",
            name = "Microcânulas 22G x 50mm",
            category = "Descartáveis",
            quantity = 35,
            unit = "unidades",
            batchLot = "CAN-772",
            expiryDate = "10/2027",
            minThreshold = 15,
            unitCost = 18.0,
            supplier = "Biometikal"
        )
    )
}
