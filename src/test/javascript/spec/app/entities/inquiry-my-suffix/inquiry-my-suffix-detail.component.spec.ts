/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { InquiryMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix-detail.component';
import { InquiryMySuffixService } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix.service';
import { InquiryMySuffix } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix.model';

describe('Component Tests', () => {

    describe('InquiryMySuffix Management Detail Component', () => {
        let comp: InquiryMySuffixDetailComponent;
        let fixture: ComponentFixture<InquiryMySuffixDetailComponent>;
        let service: InquiryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [InquiryMySuffixDetailComponent],
                providers: [
                    InquiryMySuffixService
                ]
            })
            .overrideTemplate(InquiryMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InquiryMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InquiryMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new InquiryMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.inquiry).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
