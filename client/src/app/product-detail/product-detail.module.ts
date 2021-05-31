import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductDetailRoutingModule } from './product-detail-routing.module';
import { ProductDetailComponent } from './product-detail.component';
import { RelatedComponent } from './related/related.component';


@NgModule({
  declarations: [
    ProductDetailComponent,
    RelatedComponent
  ],
  imports: [
    CommonModule,
    ProductDetailRoutingModule
  ]
})
export class ProductDetailModule { }
