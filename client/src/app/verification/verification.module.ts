import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VerificationRoutingModule } from './verification-routing.module';
import { EmailVerificationComponent } from './email-verification/email-verification.component';
import { PasswordForgotVerificationComponent } from './password-forgot-verification/password-forgot-verification.component';


@NgModule({
  declarations: [
    EmailVerificationComponent,
    PasswordForgotVerificationComponent
  ],
  imports: [
    CommonModule,
    VerificationRoutingModule
  ]
})
export class VerificationModule { }
