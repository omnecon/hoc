/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { BannerMySuffixComponent } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix.component';
import { BannerMySuffixService } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix.service';
import { BannerMySuffix } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix.model';

describe('Component Tests', () => {

    describe('BannerMySuffix Management Component', () => {
        let comp: BannerMySuffixComponent;
        let fixture: ComponentFixture<BannerMySuffixComponent>;
        let service: BannerMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [BannerMySuffixComponent],
                providers: [
                    BannerMySuffixService
                ]
            })
            .overrideTemplate(BannerMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BannerMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BannerMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new BannerMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.banners[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
