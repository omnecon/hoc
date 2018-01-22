/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { BannerMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix-detail.component';
import { BannerMySuffixService } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix.service';
import { BannerMySuffix } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix.model';

describe('Component Tests', () => {

    describe('BannerMySuffix Management Detail Component', () => {
        let comp: BannerMySuffixDetailComponent;
        let fixture: ComponentFixture<BannerMySuffixDetailComponent>;
        let service: BannerMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [BannerMySuffixDetailComponent],
                providers: [
                    BannerMySuffixService
                ]
            })
            .overrideTemplate(BannerMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BannerMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BannerMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new BannerMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.banner).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
