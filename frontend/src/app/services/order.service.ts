import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderRequest, Order } from '../models';
import { environment } from '../../environments/environment';

const API_URL = `${environment.apiUrl}/api/orders`;

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {}

  placeOrder(orderRequest: OrderRequest): Observable<Order> {
    return this.http.post<Order>(API_URL, orderRequest);
  }
}
