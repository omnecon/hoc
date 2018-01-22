/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { InquiryMySuffixComponent } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix.component';
import { InquiryMySuffixService } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix.service';
import { InquiryMySuffix } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix.model';

describe('Component Tests', () => {

    describe('InquiryMySuffix Management Component', () => {
        let comp: InquiryMySuffixComponent;
        let fixture: ComponentFixture<InquiryMySuffixComponent>;
        let service: InquiryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [InquiryMySuffixComponent],
                providers: [
                    InquiryMySuffixService
                ]
            })
            .overrideTemplate(InquiryMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InquiryMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InquiryMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new InquiryMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.inquiries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
