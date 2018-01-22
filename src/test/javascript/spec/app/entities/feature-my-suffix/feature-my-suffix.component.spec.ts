/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { FeatureMySuffixComponent } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix.component';
import { FeatureMySuffixService } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix.service';
import { FeatureMySuffix } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix.model';

describe('Component Tests', () => {

    describe('FeatureMySuffix Management Component', () => {
        let comp: FeatureMySuffixComponent;
        let fixture: ComponentFixture<FeatureMySuffixComponent>;
        let service: FeatureMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [FeatureMySuffixComponent],
                providers: [
                    FeatureMySuffixService
                ]
            })
            .overrideTemplate(FeatureMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeatureMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeatureMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new FeatureMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.features[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
