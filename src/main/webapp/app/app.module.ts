import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { Hochzeitclick11SharedModule, UserRouteAccessService } from './shared';
import { Hochzeitclick11AppRoutingModule} from './app-routing.module';
import { Hochzeitclick11HomeModule } from './home/home.module';
import { Hochzeitclick11AdminModule } from './admin/admin.module';
import { Hochzeitclick11AccountModule } from './account/account.module';
import { Hochzeitclick11EntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        Hochzeitclick11AppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        Hochzeitclick11SharedModule,
        Hochzeitclick11HomeModule,
        Hochzeitclick11AdminModule,
        Hochzeitclick11AccountModule,
        Hochzeitclick11EntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class Hochzeitclick11AppModule {}
