/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { FeatureMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix-detail.component';
import { FeatureMySuffixService } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix.service';
import { FeatureMySuffix } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix.model';

describe('Component Tests', () => {

    describe('FeatureMySuffix Management Detail Component', () => {
        let comp: FeatureMySuffixDetailComponent;
        let fixture: ComponentFixture<FeatureMySuffixDetailComponent>;
        let service: FeatureMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [FeatureMySuffixDetailComponent],
                providers: [
                    FeatureMySuffixService
                ]
            })
            .overrideTemplate(FeatureMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeatureMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeatureMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new FeatureMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.feature).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
