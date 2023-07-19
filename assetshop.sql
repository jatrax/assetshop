-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 12 Haz 2023, 14:10:52
-- Sunucu sürümü: 10.4.27-MariaDB
-- PHP Sürümü: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `assetshop`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `assetshop_products`
--

CREATE TABLE `assetshop_products` (
  `PRODUCT_ID` int(11) NOT NULL,
  `FULL_NAME` varchar(64) NOT NULL,
  `KIND` varchar(32) DEFAULT NULL,
  `ENGINE` varchar(32) DEFAULT NULL,
  `PRICE` decimal(8,2) DEFAULT NULL,
  `URL` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `assetshop_products`
--

INSERT INTO `assetshop_products` (`PRODUCT_ID`, `FULL_NAME`, `KIND`, `ENGINE`, `PRICE`, `URL`) VALUES
(1, 'Modern House', 'Yapılar', 'Unity', '180.00', 'https://assetstorev1-prd-cdn.unity3d.com/key-image/a1e39537-1063-4bf4-ba1d-935c1c61254a.jpg'),
(2, 'Cartoon Dog', 'Karakter', 'Unity', '95.00', 'https://puplore.com/wp-content/uploads/Anime-Dog-Names-Ideas-For-The-Love-Of-Kawaii.jpg'),
(3, 'Sad Cat', 'Karakter', 'Unity', '25.00', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSx-tTiQTc1QYMyYj2cXpBWlDcjRaxNd1g2NmjDW2wyXLq2RXsV8PQOcYGyrnl6oGc0Wgs&usqp=CAU'),
(4, 'Sweet Cat', 'Karakter', 'Unreal Engine 4', '50.00', 'https://img.lovepik.com/free_png/32/27/81/90258PICMFThSek80v61b_PIC2018.png_300.png'),
(5, 'Middle Earth Tree', 'Doğal Yapılar', 'Unreal Engine 5', '96.00', 'https://krita-artists.org/uploads/default/original/2X/c/c4da0984d8003144d54528dfb67fc7bd83ec64d0.jpeg'),
(6, 'AUDI R8', 'Araçlar', 'Unreal Engine 4', '160.00', 'https://cdnb.artstation.com/p/assets/covers/images/018/764/485/large/david-baylis-r8-v1-thumb.jpg?1560628573'),
(7, 'Alpha Dog', 'Karakter', 'Unity', '34.00', 'https://e7.pngegg.com/pngimages/452/297/png-clipart-pack-dog-anime-death-dog-mammal-cat-like-mammal.png'),
(8, 'Light Eye Cat', 'Karakter', 'Unreal Engine 5', '183.00', 'https://p1.hiclipart.com/preview/689/483/801/kawaii-cats-gray-and-white-cat-anime-character.jpg'),
(9, 'Adventure Tree', 'Doğal Yapılar', 'Unity', '17.00', 'https://img.itch.zone/aW1nLzIwNzM5NDIucG5n/original/%2Ffb2QS.png'),
(10, 'Big Eye Cat', 'Karakter', 'Unreal Engine 5', '61.00', 'https://www.vhv.rs/dpng/d/552-5522169_thumb-image-black-cat-anime-png-transparent-png.png'),
(11, 'Cursed Car', 'Araçlar', 'Unreal Engine 4', '42.00', 'https://media.sketchfab.com/models/f1b6808004b34ee392ad2bcd07d4f5fa/thumbnails/3b9f6fd3a9ec437ca15b2cb18795975e/c4bd3914790a4902a8fed19e394b7cf0.jpeg');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `assetshop_users`
--

CREATE TABLE `assetshop_users` (
  `USER_ID` int(11) NOT NULL,
  `USERNAME` varchar(64) NOT NULL,
  `PASSWRD` varchar(64) NOT NULL,
  `WALLET` float DEFAULT NULL,
  `OWNED` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`OWNED`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `assetshop_products`
--
ALTER TABLE `assetshop_products`
  ADD PRIMARY KEY (`PRODUCT_ID`);

--
-- Tablo için indeksler `assetshop_users`
--
ALTER TABLE `assetshop_users`
  ADD PRIMARY KEY (`USER_ID`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `assetshop_products`
--
ALTER TABLE `assetshop_products`
  MODIFY `PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Tablo için AUTO_INCREMENT değeri `assetshop_users`
--
ALTER TABLE `assetshop_users`
  MODIFY `USER_ID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
