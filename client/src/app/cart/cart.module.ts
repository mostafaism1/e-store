import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CartRoutingModule } from './cart-routing.module';
import { CartComponent } from './cart.component';
import { EmptyCartComponent } from './empty-cart/empty-cart.component';
import { InterestedComponent } from './interested/interested.component';


@NgModule({
  declarations: [
    CartComponent,
    EmptyCartComponent,
    InterestedComponent
  ],
  imports: [
    CommonModule,
    CartRoutingModule
  ]
})
export class CartModule { }
