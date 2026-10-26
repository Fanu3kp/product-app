import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { AuthService } from '../../services/auth.service';
import { Product } from '../../models';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent {
  product: Product = {
    name: '',
    description: '',
    price: 0,
    category: '',
    stock: 0,
    imageUrl: ''
  };
  loading = false;
  error = '';
  success = '';
  imagePreview: string | null = null;
  selectedFile: File | null = null;

  constructor(
    private productService: ProductService,
    public authService: AuthService,
    private router: Router
  ) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result as string;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  uploadImage(): void {
    if (!this.selectedFile) return;
    this.loading = true;
    this.error = '';
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    this.productService.uploadImage(formData).subscribe({
      next: (res: any) => {
        this.product.imageUrl = res.url;
        this.loading = false;
        this.success = 'Image uploaded successfully!';
      },
      error: (err) => {
        this.error = err.error || 'Failed to upload image';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.loading = true;
    this.error = '';
    this.success = '';
    this.productService.createProduct(this.product).subscribe({
      next: () => {
        this.success = 'Product added successfully!';
        this.loading = false;
        setTimeout(() => this.router.navigate(['/products']), 1500);
      },
      error: (err) => {
        this.error = err.error || 'Failed to add product';
        this.loading = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/products']);
  }
}
