import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { BannerComponent } from './banner/banner.component';
import { ImageSliderComponent } from './image-slider/image-slider.component';
import { MostSellingComponent } from './most-selling/most-selling.component';


@NgModule({
  declarations: [
    HomeComponent,
    BannerComponent,
    ImageSliderComponent,
    MostSellingComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule
  ]
})
export class HomeModule { }
