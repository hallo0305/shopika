-- ========================================================================
-- SHOPIKA MARKETPLACE - SUPABASE DATABASE SCHEMA & REST API SETUP
-- Projek UAS Pemrograman Mobile Dasar
-- ========================================================================

-- 1. Tabel Produk (Products)
CREATE TABLE IF NOT EXISTS public.products (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    price BIGINT NOT NULL,
    original_price BIGINT,
    discount_percent INT DEFAULT 0,
    category TEXT NOT NULL,
    image_url TEXT NOT NULL,
    rating NUMERIC(2, 1) DEFAULT 4.8,
    sold_count INT DEFAULT 0,
    stock INT DEFAULT 50,
    variants TEXT[] DEFAULT '{}',
    is_popular BOOLEAN DEFAULT false,
    is_recommended BOOLEAN DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL
);

-- 2. Tabel Keranjang Belanja (Cart Items)
CREATE TABLE IF NOT EXISTS public.cart_items (
    id TEXT PRIMARY KEY,
    product_id TEXT NOT NULL REFERENCES public.products(id) ON DELETE CASCADE,
    product_name TEXT NOT NULL,
    product_price BIGINT NOT NULL,
    product_image TEXT NOT NULL,
    variant TEXT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    stock INT NOT NULL DEFAULT 50,
    is_selected BOOLEAN DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL
);

-- 3. Tabel Pesanan & Transaksi (Orders)
CREATE TABLE IF NOT EXISTS public.orders (
    id TEXT PRIMARY KEY,
    order_number TEXT NOT NULL UNIQUE,
    items JSONB NOT NULL,
    subtotal BIGINT NOT NULL,
    shipping_fee BIGINT NOT NULL,
    total_amount BIGINT NOT NULL,
    status TEXT NOT NULL DEFAULT 'PENDING', -- 'PENDING', 'PAID', 'FAILED'
    payment_method TEXT NOT NULL,
    va_number TEXT NOT NULL,
    shipping_address TEXT NOT NULL,
    recipient_name TEXT NOT NULL,
    recipient_phone TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL
);

-- Enable Row Level Security (RLS)
ALTER TABLE public.products ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.cart_items ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.orders ENABLE ROW LEVEL SECURITY;

-- Allow public read/write for UAS evaluation purposes
CREATE POLICY "Allow public read products" ON public.products FOR SELECT USING (true);
CREATE POLICY "Allow public all on cart_items" ON public.cart_items FOR ALL USING (true);
CREATE POLICY "Allow public all on orders" ON public.orders FOR ALL USING (true);

-- Seed Data Awal Produk Shopika
INSERT INTO public.products (id, name, description, price, original_price, discount_percent, category, image_url, rating, sold_count, stock, variants, is_popular, is_recommended)
VALUES
('prod-1', 'Wireless Noise-Cancelling Headphones Pro', 'Headphone nirkabel premium dengan fitur ANC, driver 40mm hi-res, dan baterai hingga 30 jam.', 499000, 799000, 38, 'Elektronik', 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop&q=80', 4.9, 420, 25, ARRAY['Matte Black', 'Silver Gray', 'Coral Cream'], true, true),
('prod-2', 'Smartwatch AMOLED Fitness Tracker', 'Smartwatch layar AMOLED sentuh 1.43 inci, detak jantung 24/7, SpO2, GPS, water resistant 5ATM.', 349000, 550000, 36, 'Elektronik', 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop&q=80', 4.8, 850, 40, ARRAY['Obsidian Black', 'Desert Sand', 'Ocean Blue'], true, true),
('prod-3', 'Jaket Hoodie Streetwear Minimalis', 'Hoodie berbahan katun fleece 330 gsm lembut, tebal, dan hangat. Desain streetwear modern.', 189000, 250000, 24, 'Fashion', 'https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=600&auto=format&fit=crop&q=80', 4.7, 1200, 60, ARRAY['Ukuran M', 'Ukuran L', 'Ukuran XL', 'Ukuran XXL'], true, false),
('prod-4', 'Kemeja Casual Linen Breathable', 'Kemeja kasual dari bahan rami linen premium sejuk untuk iklim tropis.', 145000, 195000, 25, 'Fashion', 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&auto=format&fit=crop&q=80', 4.8, 310, 35, ARRAY['Broken White - M', 'Broken White - L', 'Sage Green - M', 'Sage Green - L'], false, true),
('prod-5', 'Sneakers Running Ultra Light Flyknit', 'Sepatu lari sol EVA empuk dan lentur dengan rajut bernapas ultra ringan 220 gram.', 289000, 420000, 31, 'Sepatu', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600&auto=format&fit=crop&q=80', 4.9, 940, 28, ARRAY['Size 39', 'Size 40', 'Size 41', 'Size 42', 'Size 43'], true, true),
('prod-6', 'Sepatu Loafers Kulit Classic Urban', 'Sepatu slip-on kasual kulit sintetis halus tahan gores dengan insole memory foam.', 225000, 310000, 27, 'Sepatu', 'https://images.unsplash.com/photo-1533867617858-e7b97e060509?w=600&auto=format&fit=crop&q=80', 4.6, 180, 15, ARRAY['Hitam - 40', 'Hitam - 41', 'Cokelat - 40', 'Cokelat - 41'], false, false),
('prod-7', 'Kacamata Hitam Polarized UV400', 'Lensa polaroid pelindung UV400 dengan frame polikarbonat kokoh nan ringan.', 85000, 135000, 37, 'Aksesori', 'https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=600&auto=format&fit=crop&q=80', 4.8, 650, 80, ARRAY['Black Gold', 'All Matte Black', 'Silver Mirror'], true, true),
('prod-8', 'Tas Ransel Laptop Waterproof Urban Minimalist', 'Backpack tahan air kompartemen busa laptop hingga 15.6 inci dengan port USB.', 175000, 260000, 32, 'Aksesori', 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=600&auto=format&fit=crop&q=80', 4.9, 1100, 45, ARRAY['Charcoal Gray', 'Midnight Navy', 'Solid Black'], true, true),
('prod-9', 'Buku Seni Menguasai Kebiasaan Positif', 'Panduan langkah demi langkah membangun kebiasaan produktif.', 88000, 110000, 20, 'Buku', 'https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&auto=format&fit=crop&q=80', 4.9, 2300, 100, ARRAY['Softcover', 'Hardcover Eksklusif'], true, true),
('prod-10', 'Buku Dasar Pemrograman Android & Kotlin', 'Panduan membuat aplikasi modern dengan Jetpack, ViewModel, Room, dan Retrofit.', 95000, 125000, 24, 'Buku', 'https://images.unsplash.com/photo-1532012164546-f432f2e37b73?w=600&auto=format&fit=crop&q=80', 5.0, 540, 30, ARRAY['Edisi Mahasiswa 2026', 'Paket Buku + Cheatsheet'], false, true),
('prod-11', 'Kopi Arabika Gayo Specialty Roast 250g', 'Biji kopi arabika Gayo single origin dengan citarasa citrus dan chocolate finish.', 68000, 85000, 20, 'Makanan', 'https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=600&auto=format&fit=crop&q=80', 4.9, 1450, 75, ARRAY['Biji Utuh', 'Giling Halus', 'Giling Sedang'], true, true),
('prod-12', 'Artisan Cookies Dark Choco Cashew 200g', 'Kue kering cokelat hitam 70% dengan kacang mede panggang tanpa bahan pengawet.', 45000, 60000, 25, 'Makanan', 'https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=600&auto=format&fit=crop&q=80', 4.8, 780, 50, ARRAY['Original Dark Choco', 'Double Choco Melt', 'Salted Caramel'], false, true)
ON CONFLICT (id) DO NOTHING;
