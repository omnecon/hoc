import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Hochzeitclick11ProfileMySuffixModule } from './profile-my-suffix/profile-my-suffix.module';
import { Hochzeitclick11StatisticMySuffixModule } from './statistic-my-suffix/statistic-my-suffix.module';
import { Hochzeitclick11FeatureMySuffixModule } from './feature-my-suffix/feature-my-suffix.module';
import { Hochzeitclick11ImageMySuffixModule } from './image-my-suffix/image-my-suffix.module';
import { Hochzeitclick11GalleryMySuffixModule } from './gallery-my-suffix/gallery-my-suffix.module';
import { Hochzeitclick11InquiryMySuffixModule } from './inquiry-my-suffix/inquiry-my-suffix.module';
import { Hochzeitclick11BannerMySuffixModule } from './banner-my-suffix/banner-my-suffix.module';
import { Hochzeitclick11DataImportMySuffixModule } from './data-import-my-suffix/data-import-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Hochzeitclick11ProfileMySuffixModule,
        Hochzeitclick11StatisticMySuffixModule,
        Hochzeitclick11FeatureMySuffixModule,
        Hochzeitclick11ImageMySuffixModule,
        Hochzeitclick11GalleryMySuffixModule,
        Hochzeitclick11InquiryMySuffixModule,
        Hochzeitclick11BannerMySuffixModule,
        Hochzeitclick11DataImportMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Hochzeitclick11EntityModule {}
