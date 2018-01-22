/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { InquiryMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix-dialog.component';
import { InquiryMySuffixService } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix.service';
import { InquiryMySuffix } from '../../../../../../main/webapp/app/entities/inquiry-my-suffix/inquiry-my-suffix.model';

describe('Component Tests', () => {

    describe('InquiryMySuffix Management Dialog Component', () => {
        let comp: InquiryMySuffixDialogComponent;
        let fixture: ComponentFixture<InquiryMySuffixDialogComponent>;
        let service: InquiryMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [InquiryMySuffixDialogComponent],
                providers: [
                    InquiryMySuffixService
                ]
            })
            .overrideTemplate(InquiryMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InquiryMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InquiryMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InquiryMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.inquiry = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'inquiryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InquiryMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.inquiry = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'inquiryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
