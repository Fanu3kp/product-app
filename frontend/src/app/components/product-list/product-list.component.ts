import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { OrderService } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { Product, OrderRequest } from '../../models';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  categories: string[] = [];
  selectedCategory = 'all';
  loading = false;
  error = '';
  ordering = false;
  orderProductId: number | null = null;
  orderQuantity = 1;
  orderEmail = '';
  orderError = '';
  orderSuccess = '';

  private readonly USD_TO_KSH = 150;

  constructor(
    public authService: AuthService,
    private productService: ProductService,
    private orderService: OrderService
  ) {}

  usdToKsh(usd: number): number {
    return Math.round(usd * this.USD_TO_KSH * 100) / 100;
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.productService.getAllProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.categories = Array.from(new Set(products.map(p => p.category)));
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load products';
        this.loading = false;
      }
    });
  }

  get filteredProducts(): Product[] {
    if (this.selectedCategory === 'all') {
      return this.products;
    }
    return this.products.filter(p => p.category === this.selectedCategory);
  }

  onCategoryChange(): void {
    // Filter is computed via filteredProducts getter
  }

  startOrder(product: Product): void {
    this.orderProductId = product.id!;
    this.orderQuantity = 1;
    this.orderEmail = '';
    this.orderError = '';
    this.orderSuccess = '';
  }

  cancelOrder(): void {
    this.orderProductId = null;
  }

  placeOrder(): void {
    if (!this.orderProductId || !this.orderEmail) {
      this.orderError = 'Please enter your email';
      return;
    }

    this.ordering = true;
    const orderRequest: OrderRequest = {
      productId: this.orderProductId,
      quantity: this.orderQuantity,
      customerEmail: this.orderEmail
    };

    this.orderService.placeOrder(orderRequest).subscribe({
      next: () => {
        this.orderSuccess = 'Order placed successfully! Check your email for confirmation.';
        this.orderError = '';
        this.ordering = false;
        this.orderProductId = null;
        this.loadProducts();
      },
      error: (err) => {
        this.orderError = err.error || 'Failed to place order';
        this.ordering = false;
      }
    });
  }
}
