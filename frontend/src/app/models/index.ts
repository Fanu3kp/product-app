export interface Product {
  id?: number;
  name: string;
  description?: string;
  price: number;
  category: string;
  stock: number;
  imageUrl?: string;
}

export interface User {
  id?: number;
  username: string;
  password?: string;
  email: string;
  role: string;
}

export interface Order {
  id?: number;
  productId: number;
  productName: string;
  quantity: number;
  customerEmail: string;
  orderDate?: string;
  status?: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  role: string;
}

export interface OrderRequest {
  productId: number;
  quantity: number;
  customerEmail: string;
}
