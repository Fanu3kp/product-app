import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { APP_INITIALIZER } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { AdminGuard } from './guards/admin.guard';
import { AuthService, initAuth } from './services/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    AddProductComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    AuthService,
    AdminGuard,
    { provide: APP_INITIALIZER, useFactory: initAuth, deps: [AuthService], multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
