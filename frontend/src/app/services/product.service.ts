import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models';
import { environment } from '../../environments/environment';

const API_URL = `${environment.apiUrl}/api/products`;

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(API_URL);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${API_URL}/${id}`);
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(API_URL, product);
  }

  updateProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${API_URL}/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${API_URL}/${id}`);
  }

  uploadImage(file: FormData): Observable<any> {
    return this.http.post('http://localhost:8080/api/upload/image', file);
  }
}
