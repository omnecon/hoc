/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { FeatureMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix-dialog.component';
import { FeatureMySuffixService } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix.service';
import { FeatureMySuffix } from '../../../../../../main/webapp/app/entities/feature-my-suffix/feature-my-suffix.model';
import { ProfileMySuffixService } from '../../../../../../main/webapp/app/entities/profile-my-suffix';

describe('Component Tests', () => {

    describe('FeatureMySuffix Management Dialog Component', () => {
        let comp: FeatureMySuffixDialogComponent;
        let fixture: ComponentFixture<FeatureMySuffixDialogComponent>;
        let service: FeatureMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [FeatureMySuffixDialogComponent],
                providers: [
                    ProfileMySuffixService,
                    FeatureMySuffixService
                ]
            })
            .overrideTemplate(FeatureMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeatureMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeatureMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FeatureMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.feature = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'featureListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FeatureMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.feature = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'featureListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
