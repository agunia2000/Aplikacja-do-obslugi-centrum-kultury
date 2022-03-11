-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Czas generowania: 07 Mar 2022, 22:15
-- Wersja serwera: 8.0.27
-- Wersja PHP: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `projekt`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `events`
--

CREATE TABLE `events` (
  `eventID` int NOT NULL,
  `name` text COLLATE utf8mb4_general_ci,
  `date` text COLLATE utf8mb4_general_ci,
  `time` text COLLATE utf8mb4_general_ci,
  `priceOfTicket` text COLLATE utf8mb4_general_ci,
  `description` text COLLATE utf8mb4_general_ci,
  `type` text COLLATE utf8mb4_general_ci,
  `nameOFHall` text COLLATE utf8mb4_general_ci,
  `occupancyOfHall` text COLLATE utf8mb4_general_ci,
  `minimalAge` int DEFAULT NULL,
  `pathToPoster` text COLLATE utf8mb4_general_ci,
  `takenPlaces` text COLLATE utf8mb4_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `events`
--

INSERT INTO `events` (`eventID`, `name`, `date`, `time`, `priceOfTicket`, `description`, `type`, `nameOFHall`, `occupancyOfHall`, `minimalAge`, `pathToPoster`, `takenPlaces`) VALUES
(1, 'Dawid Podsiadło', '20.09.2021', '18:30', '199.00', 'Dawid Podsiadło w akustycznej trasie! Zgodnie z zapowiedzią złożoną fanom ze sceny podczas niesamowitego koncertu na PGE Narodowym, Dawid ogłasza nową serię koncertów.', 'koncert', 'S3', '100%', 12, 'src\\posters\\podsiadlo.jpg', 'D7,D8,D9,C12,G8,G9,G10,'),
(2, 'Dunkierka', '05.07.2021', '17:25', '19.99', 'Wojska alianckie zostają przyparte do morza pod Dunkierką. Bitwa staje się sprawdzianem dla młodych żołnierzy, pilota RAF-u oraz załogi cywilnej łodzi płynącej przez Kanał La Manche.', 'film', 'S3', '100%', 15, 'src\\posters\\dunkirk.jpg', ''),
(4, 'Klimakterium', '07.07.2021', '15:30', '75.00', 'Sztuka ma pokazywać owe różne zachowania, zawsze jednak w sposób humorystyczny - tym bardziej, że sam humor jest doskonałym lekarstwem (bez skutków ubocznych).', 'inne', 'S1', '100%', 18, 'src\\posters\\klimakterium.jpg', 'E5,E6,E7,E8,H9,'),
(5, 'Boże Ciało', '25.03.2021', '19:45', '19.99', 'Dwudziestoletni Daniel zostaje warunkowo zwolniony z poprawczaka. Wyjeżdża na drugi koniec Polski, żeby pracować w stolarni, ale zamiast tego zaczyna udawać księdza.', 'film', 'S2', '50%', 15, 'src\\posters\\boze_ciało.jpg', 'E11,'),
(8, 'Potworna rodzinka', '17.11.2021', '15:30', '9.99', 'Aby uwolnić Babę Jagę i Renfielda ze szponów łowczyni potworów Mili Starr, członkowie rodziny Wishbone ponownie przekształcają się odpowiednio w wampira, potwora Frankensteina, mumię i wilkołaka.', 'film', 'S2', '100%', 0, 'src\\posters\\rodzinka.jpg', 'I7,I8,'),
(10, 'Krzysztof Zalewski', '20.06.2021', '20:00', '99.00', '', 'koncert', 'S2', '100%', 0, '', '');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `halls`
--

CREATE TABLE `halls` (
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `numberOfRows` int NOT NULL,
  `numberOfPlacesInRows` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `halls`
--

INSERT INTO `halls` (`name`, `numberOfRows`, `numberOfPlacesInRows`) VALUES
('S1', 8, 12),
('S2', 10, 16),
('S3', 10, 20);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `invoices`
--

CREATE TABLE `invoices` (
  `name` text COLLATE utf8mb4_general_ci,
  `surname` text COLLATE utf8mb4_general_ci,
  `nameOfCompany` text COLLATE utf8mb4_general_ci,
  `NIP` text COLLATE utf8mb4_general_ci,
  `address` text COLLATE utf8mb4_general_ci,
  `address_cont` text COLLATE utf8mb4_general_ci,
  `phoneNumber` text COLLATE utf8mb4_general_ci,
  `email` text COLLATE utf8mb4_general_ci,
  `cost` text COLLATE utf8mb4_general_ci,
  `tickets` text COLLATE utf8mb4_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `invoices`
--

INSERT INTO `invoices` (`name`, `surname`, `nameOfCompany`, `NIP`, `address`, `address_cont`, `phoneNumber`, `email`, `cost`, `tickets`) VALUES
('Piotr', 'Magdziak', 'MAGTRANS', '123456789', 'ul. Bohaterów Warszawy 5', '28-100 Busko-Zdrój', '505139486', 'piotr@magtrans.pl', '199,00', '1_C12,'),
('', '', '', '', '', '', '', '', '75,00', '4_H9,');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`eventID`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `events`
--
ALTER TABLE `events`
  MODIFY `eventID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
